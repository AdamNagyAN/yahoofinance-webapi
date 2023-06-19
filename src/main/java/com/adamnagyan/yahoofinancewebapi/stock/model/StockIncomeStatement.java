package com.adamnagyan.yahoofinancewebapi.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockIncomeStatement {

	public Date endDate;

	public Long totalRevenue;

	public Long costOfRevenue;

	public Long grossProfit;

	public Long researchDevelopment;

	public Long sellingGeneralAdministrative;

	public Long nonRecurring;

	public Long otherOperatingExpenses;

	public Long totalOperatingExpenses;

	public Long operatingIncome;

	public Long totalOtherIncomeExpenseNet;

	public Long ebit;

	public Long interestExpense;

	public Long incomeBeforeTax;

	public Long incomeTaxExpense;

	public Long minorityInterest;

	public Long netIncomeFromContinuingOps;

	public Long discontinuedOperations;

	public Long extraordinaryItems;

	public Long effectOfAccountingCharges;

	public Long otherItems;

	public Long netIncome;

	public Long netIncomeApplicableToCommonShares;

	public StockIncomeStatement add(StockIncomeStatement that) {
		return StockIncomeStatement.builder()
			.endDate(this.endDate)
			.totalRevenue(this.totalRevenue + that.totalRevenue)
			.totalRevenue(this.totalRevenue + that.totalRevenue)
			.costOfRevenue(this.costOfRevenue + that.costOfRevenue)
			.grossProfit(this.grossProfit + that.grossProfit)
			.researchDevelopment(this.researchDevelopment + that.researchDevelopment)
			.sellingGeneralAdministrative(this.sellingGeneralAdministrative + that.sellingGeneralAdministrative)
			.nonRecurring(this.nonRecurring + that.nonRecurring)
			.otherOperatingExpenses(this.otherOperatingExpenses + that.otherOperatingExpenses)
			.totalOperatingExpenses(this.totalOperatingExpenses + that.totalOperatingExpenses)
			.operatingIncome(this.operatingIncome + that.operatingIncome)
			.totalOtherIncomeExpenseNet(this.totalOtherIncomeExpenseNet + that.totalOtherIncomeExpenseNet)
			.ebit(this.ebit + that.ebit)
			.interestExpense(this.interestExpense + that.interestExpense)
			.incomeBeforeTax(this.incomeBeforeTax + that.incomeBeforeTax)
			.incomeTaxExpense(this.incomeTaxExpense + that.incomeTaxExpense)
			.minorityInterest(this.minorityInterest + that.minorityInterest)
			.netIncomeFromContinuingOps(this.netIncomeFromContinuingOps + that.netIncomeFromContinuingOps)
			.discontinuedOperations(this.discontinuedOperations + that.discontinuedOperations)
			.extraordinaryItems(this.extraordinaryItems + that.extraordinaryItems)
			.effectOfAccountingCharges(this.effectOfAccountingCharges + that.effectOfAccountingCharges)
			.otherItems(this.otherItems + that.otherItems)
			.netIncome(this.netIncome + that.netIncome)
			.netIncomeApplicableToCommonShares(
					this.netIncomeApplicableToCommonShares + that.netIncomeApplicableToCommonShares)
			.build();
	}

}
