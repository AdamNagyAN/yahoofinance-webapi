package com.adamnagyan.yahoofinancewebapi.services.history;

import com.adamnagyan.yahoofinancewebapi.model.history.DividendHistory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Calendar;

@Service
@AllArgsConstructor
public class DividendHistoryServiceImpl implements DividendHistoryService {

  @Override
  public DividendHistory findStockByTicker(String ticker) {
    DividendHistory dividendHistory = new DividendHistory();
    Calendar cal = Calendar.getInstance();
    cal.set(2013, 1, 1);
    try {
      dividendHistory.setSymbol(YahooFinance.get(ticker).getSymbol());
      dividendHistory.setCompanyName(YahooFinance.get(ticker).getName());
      dividendHistory.setHistoricalDividends(YahooFinance.get(ticker).getDividendHistory(cal));
      System.out.println(cal.getTime());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return dividendHistory;
  }
}
