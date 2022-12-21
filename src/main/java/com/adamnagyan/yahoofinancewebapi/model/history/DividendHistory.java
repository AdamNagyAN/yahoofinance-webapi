package com.adamnagyan.yahoofinancewebapi.model.history;

import lombok.Data;
import yahoofinance.histquotes2.HistoricalDividend;

import java.util.List;

@Data
public class DividendHistory {

  private String symbol;
  private String companyName;
  private List<HistoricalDividend> historicalDividends;

}
