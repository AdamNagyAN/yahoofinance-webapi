package com.adamnagyan.yahoofinancewebapi.services.dividend_percentage;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.DividendPercentageHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.PriceDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.services.history.DividendHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DividendPercentageHistoryServiceImpl implements DividendPercentageHistoryService {

  private final DividendPercentageHistoryMapper dividendPercentageHistoryMapper;
  private final DividendHistoryService dividendHistoryService;

  @Override
  public List<PriceDto> getPriceHistory(String symbol) throws IOException, BadRequestException {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -6);

    Stock stock = YahooFinance.get(symbol);
    if (stock == null) {
      throw new BadRequestException("symbol", "Symbol was not found!");
    }

    return dividendPercentageHistoryMapper.toPriceListDto(stock.getHistory(cal, Interval.DAILY));
  }

  @Override
  public DividendPercentageHistoryDto getDividendPercentageHistoryDto(String symbol) throws IOException, BadRequestException {
    Stock stock = YahooFinance.get(symbol);
    if (stock == null) {
      throw new BadRequestException("symbol", "Symbol was not found!");
    }
    List<PriceDto> prices = getPriceHistory(symbol);
    List<DividendDto> dividendDtoList = dividendHistoryService.findStockByTicker(symbol, "5y").getHistoricalDividends();
    int currentIndex = 0;
    List<DividendPercentageDto> dividendPercentageDtoList = new ArrayList<>();

    for (PriceDto price : prices) {
      while (currentIndex - 1 >= dividendDtoList.size() && price.getDate().isAfter(dividendDtoList.get(currentIndex + 1).getDate())) {
        currentIndex++;
      }
      dividendPercentageDtoList.add(dividendPercentageHistoryMapper.toDividendPercentageDto(
              dividendDtoList.get(currentIndex).getAdjDividend() * 4 / price.getPrice() * 100,
              price.getDate()
      ));
    }

    double currentDividendYield = stock.getDividend(true).getAnnualYieldPercent().doubleValue();
    double averageDividendYield = dividendPercentageDtoList.stream().mapToDouble(DividendPercentageDto::getDividendPercentage).average().orElse(Double.NaN);


    return dividendPercentageHistoryMapper.toDividendPercentageHistoryDto(dividendPercentageDtoList, currentDividendYield, averageDividendYield);
  }
}
