package com.adamnagyan.yahoofinancewebapi.api.v1.model.history;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DividendHistoryDto {
  private String symbol;
  private String companyName;
  private List<DividendDto> historicalDividends;
  private List<String> validTimeFrames;
}
