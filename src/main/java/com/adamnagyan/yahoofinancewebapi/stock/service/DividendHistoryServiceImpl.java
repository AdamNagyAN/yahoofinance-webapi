package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendHistoryDto;
import com.adamnagyan.yahoofinancewebapi.stock.mapper.DividendHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockTimeFrames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Caching
@Slf4j
public class DividendHistoryServiceImpl implements DividendHistoryService {

	public static final String DIVIDEND_HISTORY_KEY = "dividend-history";

	private final ApplicationContext applicationContext;

	private final DividendHistoryMapper dividendHistoryMapper;

	private DividendHistoryService getDividendHistoryService() {
		return applicationContext.getBean(DividendHistoryService.class);
	}

	@Override
	public DividendHistoryDto findStockByTicker(String ticker, String timeframe)
			throws IOException, BadRequestException {
		LocalDate date = LocalDate.now().plusYears(StockTimeFrames.getTimeFrameByName(timeframe).getValue());

		DividendHistoryDto cachedData = getDividendHistoryService().findStockByTicker(ticker);

		return new DividendHistoryDto(cachedData.getHistoricalDividends()
			.stream()
			.filter(dividendDto -> dividendDto.getDate().isAfter(date))
			.toList(), cachedData.getValidTimeFrames(), cachedData.getDivGrowthRates());
	}

	private void putDgrToMap(Map<String, Double> divGrowthRates, DividendDto lastDividend, DividendDto dividendDto,
			int years) {
		String key = "dgr" + years;
		if (divGrowthRates.get(key) == null
				&& dividendDto.getDate().isBefore(lastDividend.getDate().minusYears(years))) {
			divGrowthRates.put(key,
					calculateDgrFormula(lastDividend.getAdjDividend(), dividendDto.getAdjDividend(), years));
			log.info(key + ": " + dividendDto + " = " + divGrowthRates.get(key));
		}
	}

	@Override
	@Cacheable(cacheNames = DIVIDEND_HISTORY_KEY, key = "#ticker")
	public DividendHistoryDto findStockByTicker(String ticker) throws IOException, BadRequestException {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, StockTimeFrames.MAX.getValue());

		Stock stock = YahooFinance.get(ticker, cal);
		if (stock == null) {
			throw new BadRequestException("symbol", "Symbol was not found!");
		}

		List<DividendDto> historicalDividends = dividendHistoryMapper.toDividenDtoList(stock.getDividendHistory(cal));

		Map<String, Double> divGrowthRates = new HashMap<>();

		DividendDto lastDividend = historicalDividends.get(historicalDividends.size() - 1);
		for (int i = historicalDividends.size() - 1; i >= 0; i--) {
			DividendDto dividendDto = historicalDividends.get(i);
			putDgrToMap(divGrowthRates, lastDividend, dividendDto, 1);
			putDgrToMap(divGrowthRates, lastDividend, dividendDto, 3);
			putDgrToMap(divGrowthRates, lastDividend, dividendDto, 5);
			putDgrToMap(divGrowthRates, lastDividend, dividendDto, 10);
		}

		return dividendHistoryMapper.toDTO(0, historicalDividends,
				Arrays.stream(StockTimeFrames.values()).map(StockTimeFrames::getName).collect(Collectors.toList()),
				divGrowthRates);
	}

	private Double calculateDgrFormula(double end, double start, int years) {
		if (start == 0.0 || years == 0.0)
			return 0.0;
		return Math.pow(end / start, 1.0 / years) - 1;
	}

	@Scheduled(fixedRateString = "${caching.spring.dividend-history}")
	@CacheEvict(value = DIVIDEND_HISTORY_KEY, allEntries = true)
	public void emptyDividendHistoryCache() {
	}

}
