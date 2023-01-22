package com.adamnagyan.yahoofinancewebapi.services.dividend_percentage;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.DividendPercentageHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.PriceDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.model.history.StockTimeFrames;
import com.adamnagyan.yahoofinancewebapi.services.history.DividendHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Caching
public class DividendPercentageHistoryServiceImpl implements DividendPercentageHistoryService {
  public static final String PRICE_KEY = "price-key";
  private final DividendPercentageHistoryMapper dividendPercentageHistoryMapper;
  private final DividendHistoryService dividendHistoryService;
  private final ApplicationContext applicationContext;


  private DividendPercentageHistoryService getDividendPercentageHistoryService() {
    return applicationContext.getBean(DividendPercentageHistoryService.class);
  }

  @Override
  @Cacheable(value = PRICE_KEY, key = "#symbol")
  public List<PriceDto> getPriceHistory(String symbol) throws IOException, BadRequestException {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, StockTimeFrames.MAX.getValue());

    Stock stock = YahooFinance.get(symbol);
    if (stock == null) {
      throw new BadRequestException("symbol", "Symbol was not found!");
    }

    return dividendPercentageHistoryMapper.toPriceListDto(stock.getHistory(cal, Interval.DAILY));
  }

  @CacheEvict(value = PRICE_KEY, allEntries = true)
  @Scheduled(fixedRateString = "${caching.spring.price-history}")
  public void emptyPriceCache() {
  }


  @Override
  public DividendPercentageHistoryDto getDividendPercentageHistoryDto(String symbol, String timeframe) throws IOException, BadRequestException {
    Stock stock = YahooFinance.get(symbol);
    if (stock == null) {
      throw new BadRequestException("symbol", "Symbol was not found!");
    }


    List<PriceDto> prices = getDividendPercentageHistoryService().getPriceHistory(symbol);
    List<DividendDto> dividendDtoList = dividendHistoryService.findStockByTicker(symbol, timeframe).getHistoricalDividends();
    int currentIndex = 0;
    List<DividendPercentageDto> dividendPercentageDtoList = new ArrayList<>();

    for (PriceDto price : prices) {
      while (currentIndex < dividendDtoList.size() - 1 && price.getDate().isAfter(dividendDtoList.get(currentIndex + 1).getDate())) {
        System.out.println(currentIndex + "price: " + price.getDate() + "div:" + dividendDtoList.get(currentIndex).getDate());
        currentIndex++;
      }
      if (price.getDate().isAfter(dividendDtoList.get(currentIndex).getDate())) {
        dividendPercentageDtoList.add(dividendPercentageHistoryMapper.toDividendPercentageDto(
                dividendDtoList.get(currentIndex).getAdjDividend() * 4 / price.getPrice() * 100,
                price.getDate()));
      }
    }

    double currentDividendYield = stock.getDividend(true).getAnnualYieldPercent().doubleValue();
    double averageDividendYield = dividendPercentageDtoList.stream().mapToDouble(DividendPercentageDto::getDividendPercentage).average().orElse(Double.NaN);

    List<String> validTimeFrames = Arrays.stream(StockTimeFrames.values())
            .map(StockTimeFrames::getName)
            .collect(Collectors.toList());

    return dividendPercentageHistoryMapper.toDividendPercentageHistoryDto(dividendPercentageDtoList, currentDividendYield, averageDividendYield, validTimeFrames);
  }

}
