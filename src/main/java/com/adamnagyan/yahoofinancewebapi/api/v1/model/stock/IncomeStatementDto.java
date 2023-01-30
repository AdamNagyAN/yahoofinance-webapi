package com.adamnagyan.yahoofinancewebapi.api.v1.model.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IncomeStatementDto {
  public LocalDate endDate;
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
}
