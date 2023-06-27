package com.adamnagyan.yahoofinancewebapi.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockIncomeStatement {

	public StatementValue endDate = new StatementValue();

	public StatementValue totalRevenue = new StatementValue();

	public StatementValue costOfRevenue = new StatementValue();

	public StatementValue grossProfit = new StatementValue();

	public StatementValue researchDevelopment = new StatementValue();

	public StatementValue sellingGeneralAdministrative = new StatementValue();

	public StatementValue nonRecurring = new StatementValue();

	public StatementValue otherOperatingExpenses = new StatementValue();

	public StatementValue totalOperatingExpenses = new StatementValue();

	public StatementValue operatingIncome = new StatementValue();

	public StatementValue totalOtherIncomeExpenseNet = new StatementValue();

	public StatementValue ebit = new StatementValue();

	public StatementValue interestExpense = new StatementValue();

	public StatementValue incomeBeforeTax = new StatementValue();

	public StatementValue incomeTaxExpense = new StatementValue();

	public StatementValue minorityInterest = new StatementValue();

	public StatementValue netIncomeFromContinuingOps = new StatementValue();

	public StatementValue discontinuedOperations = new StatementValue();

	public StatementValue extraordinaryItems = new StatementValue();

	public StatementValue effectOfAccountingCharges = new StatementValue();

	public StatementValue otherItems = new StatementValue();

	public StatementValue netIncome = new StatementValue();

	public StatementValue netIncomeApplicableToCommonShares = new StatementValue();

}
