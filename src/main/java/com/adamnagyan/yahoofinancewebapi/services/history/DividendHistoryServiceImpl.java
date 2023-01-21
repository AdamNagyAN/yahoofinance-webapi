package com.adamnagyan.yahoofinancewebapi.services.history;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.DividendHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendHistoryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.model.history.StockTimeFrames;
import lombok.RequiredArgsConstructor;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Caching
public class DividendHistoryServiceImpl implements DividendHistoryService {
  public static final String DIVIDEND_HISTORY_KEY = "dividend-history";
  private final ApplicationContext applicationContext;
  private final DividendHistoryMapper dividendHistoryMapper;


  private DividendHistoryService getDividendHistoryService() {
    return applicationContext.getBean(DividendHistoryService.class);
  }

  @Override
  public DividendHistoryDto findStockByTicker(String ticker, String timeframe) throws IOException, BadRequestException {
    LocalDate date = LocalDate.now().plusYears(StockTimeFrames.getTimeFrameByName(timeframe).getValue());

    DividendHistoryDto cachedData = getDividendHistoryService().findStockByTicker(ticker);


    return new DividendHistoryDto(cachedData.getHistoricalDividends().stream()
            .filter(dividendDto -> dividendDto.getDate().isAfter(date)).toList(), cachedData.getValidTimeFrames());
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

    DividendHistoryDto result = dividendHistoryMapper.toDTO(0, stock.getDividendHistory(cal));
    result.setValidTimeFrames(
            Arrays.stream(StockTimeFrames.values())
                    .map(StockTimeFrames::getName)
                    .collect(Collectors.toList()));
    return result;
  }

  @Scheduled(fixedRateString = "${caching.spring.dividend-history}")
  @CacheEvict(value = DIVIDEND_HISTORY_KEY, allEntries = true)
  public void emptyDividendHistoryCache() {
  }

}
