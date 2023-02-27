package com.adamnagyan.yahoofinancewebapi.api.v1.model.stock;

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
