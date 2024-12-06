package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DividendPercentageHistoryDto {

	private List<DividendPercentageDto> historicalDividendPercentages;

	private double averageDividendPercentage;

	private double currentDividendPercentage;

	private List<String> validTimeFrames;

}
