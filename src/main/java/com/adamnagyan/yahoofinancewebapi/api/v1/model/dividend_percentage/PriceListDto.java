package com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PriceListDto {
  private List<PriceDto> historicalPrices;
}
