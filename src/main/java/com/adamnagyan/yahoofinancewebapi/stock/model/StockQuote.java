package com.adamnagyan.yahoofinancewebapi.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockQuote {
  private String region;
  private String typeDisp;
  private String currency;
  private String exchange;
  private String longName;
  private String marketState;
  private double regularMarketChangePercent;
  private double regularMarketPrice;
  private String averageAnalystRating;
  private String regularMarketDayRange;
  private int dividendDate;
  private double trailingPE;
  private double dividendRate;
  private double dividendYield;
  private double epsForward;
  private double epsCurrentYear;
  private double fiftyDayAverage;
  private double twoHundredDayAverage;
  private long marketCap;
  private double forwardPE;
  private String displayName;
  private String symbol;
}
