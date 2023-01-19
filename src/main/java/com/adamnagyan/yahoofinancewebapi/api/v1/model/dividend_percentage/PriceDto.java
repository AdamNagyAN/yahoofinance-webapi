package com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PriceDto {

  private LocalDate date;
  private double price;

}
