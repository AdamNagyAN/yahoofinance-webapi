package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CashflowStatementDto {

	private LocalDate endDate;

	private Long totalCashFromOperatingActivities;

	private Long netIncome;

	private Long totalCashflowsFromInvestingActivities;

	private Long otherCashflowsFromInvestingActivities;

	private Long totalCashFromFinancingActivities;

	private Long otherCashflowsFromFinancingActivities;

	private Long depreciation;

	private Long capitalExpenditures;

	private Long investments;

	private Long dividendsPaid;

	private Long netBorrowings;

	private Long effectOfExchangeRate;

	private Long changeInCash;

	private Long issuanceOfStock;

	private Long freeCashFlow;

}
