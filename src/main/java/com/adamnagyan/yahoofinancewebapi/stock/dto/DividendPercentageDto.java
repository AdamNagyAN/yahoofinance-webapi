package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DividendPercentageDto {

	private LocalDate date;

	private double dividendPercentage;

}
