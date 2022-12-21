package com.adamnagyan.yahoofinancewebapi.api.v1.model.history;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DividendDto {
  private LocalDate date;
  private double adjDividend;
}
