package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class StockSummaryDto {

	String companyName;

	String symbol;

	private BigDecimal marketCap;

	private BigDecimal eps;

	private BigDecimal pe;

	private BigDecimal oneYearTargetPrice;

	private BigDecimal price;

	private LocalDate earningsAnnouncement;

	private BigDecimal annualDividendYield;

	private BigDecimal annualDividendYieldPercent;

}
