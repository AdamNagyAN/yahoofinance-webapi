package com.adamnagyan.yahoofinancewebapi.model.stock;

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
  public Long totalRevenue = 0L;
  public Long costOfRevenue = 0L;
  public Long grossProfit = 0L;
  public Long researchDevelopment = 0L;
  public Long sellingGeneralAdministrative = 0L;
  public Long nonRecurring = 0L;
  public Long otherOperatingExpenses = 0L;
  public Long totalOperatingExpenses = 0L;
  public Long operatingIncome = 0L;
  public Long totalOtherIncomeExpenseNet = 0L;
  public Long ebit = 0L;
  public Long interestExpense = 0L;
  public Long incomeBeforeTax = 0L;
  public Long incomeTaxExpense = 0L;
  public Long minorityInterest = 0L;
  public Long netIncomeFromContinuingOps = 0L;
  public Long discontinuedOperations = 0L;
  public Long extraordinaryItems = 0L;
  public Long effectOfAccountingCharges = 0L;
  public Long otherItems = 0L;
  public Long netIncome = 0L;
  public Long netIncomeApplicableToCommonShares = 0L;

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
            .netIncomeApplicableToCommonShares(this.netIncomeApplicableToCommonShares + that.netIncomeApplicableToCommonShares)
            .build();
  }

}
