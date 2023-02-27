package com.adamnagyan.yahoofinancewebapi.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class StockCashflowStatement {

	private Date endDate;

	private Long totalCashFromOperatingActivities = 0L;

	private Long netIncome = 0L;

	private Long totalCashflowsFromInvestingActivities = 0L;

	private Long otherCashflowsFromInvestingActivities = 0L;

	private Long totalCashFromFinancingActivities = 0L;

	private Long otherCashflowsFromFinancingActivities = 0L;

	private Long depreciation = 0L;

	private Long capitalExpenditures = 0L;

	private Long investments = 0L;

	private Long dividendsPaid = 0L;

	private Long netBorrowings = 0L;

	private Long effectOfExchangeRate = 0L;

	private Long changeInCash = 0L;

	private Long issuanceOfStock = 0L;

	private Long freeCashFlow = 0L;

	public StockCashflowStatement add(StockCashflowStatement that) {
		return StockCashflowStatement.builder()
			.endDate(this.endDate)
			.totalCashFromOperatingActivities(
					this.totalCashFromOperatingActivities + that.totalCashFromOperatingActivities)
			.netIncome(this.netIncome + that.netIncome)
			.totalCashflowsFromInvestingActivities(
					this.totalCashflowsFromInvestingActivities + that.totalCashflowsFromInvestingActivities)
			.otherCashflowsFromInvestingActivities(
					this.otherCashflowsFromInvestingActivities + that.otherCashflowsFromInvestingActivities)
			.totalCashFromFinancingActivities(
					this.totalCashFromFinancingActivities + that.totalCashFromFinancingActivities)
			.depreciation(this.depreciation + that.depreciation)
			.capitalExpenditures(this.capitalExpenditures + that.capitalExpenditures)
			.investments(this.investments + that.investments)
			.dividendsPaid(this.dividendsPaid + that.dividendsPaid)
			.netBorrowings(this.netBorrowings + that.netBorrowings)
			.effectOfExchangeRate(this.effectOfExchangeRate + that.effectOfExchangeRate)
			.changeInCash(this.changeInCash + that.changeInCash)
			.issuanceOfStock(this.issuanceOfStock + that.issuanceOfStock)
			.freeCashFlow(this.freeCashFlow + that.freeCashFlow)
			.build();
	}

}
