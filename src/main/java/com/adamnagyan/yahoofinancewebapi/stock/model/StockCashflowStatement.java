package com.adamnagyan.yahoofinancewebapi.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class StockCashflowStatement {

	private StatementValue endDate;

	private StatementValue totalCashFromOperatingActivities = new StatementValue();

	private StatementValue netIncome = new StatementValue();

	private StatementValue totalCashflowsFromInvestingActivities = new StatementValue();

	private StatementValue otherCashflowsFromInvestingActivities = new StatementValue();

	private StatementValue totalCashFromFinancingActivities = new StatementValue();

	private StatementValue otherCashflowsFromFinancingActivities = new StatementValue();

	private StatementValue depreciation = new StatementValue();

	private StatementValue capitalExpenditures = new StatementValue();

	private StatementValue investments = new StatementValue();

	private StatementValue dividendsPaid = new StatementValue();

	private StatementValue netBorrowings = new StatementValue();

	private StatementValue effectOfExchangeRate = new StatementValue();

	private StatementValue changeInCash = new StatementValue();

	private StatementValue issuanceOfStock = new StatementValue();

	private StatementValue freeCashFlow = new StatementValue();

}
