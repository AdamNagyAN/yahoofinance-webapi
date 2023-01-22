package com.adamnagyan.yahoofinancewebapi.api.v1.model.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DividendHistoryDto {
  private List<DividendDto> historicalDividends;
  private List<String> validTimeFrames;
}
