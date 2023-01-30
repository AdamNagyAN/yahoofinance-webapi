package com.adamnagyan.yahoofinancewebapi.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class StockBalanceSheet {
  private StatementValue endDate;
  private StatementValue cash;
  private StatementValue shortTermInvestments;
  private StatementValue netReceivables;
  private StatementValue inventory;
  private StatementValue otherCurrentAssets;
  private StatementValue totalCurrentAssets;
  private StatementValue longTermInvestments;
  private StatementValue propertyPlantEquipment;
  private StatementValue goodWill;
  private StatementValue otherAssets;
  private StatementValue deferredLongTermAssetCharges;
  private StatementValue totalAssets;
  private StatementValue accountsPayable;
  private StatementValue shortLongTermDebt;
  private StatementValue otherCurrentLiab;
  private StatementValue longTermDebt;
  private StatementValue otherLiab;
  private StatementValue deferredLongTermLiab;
  private StatementValue minorityInterest;
  private StatementValue totalCurrentLiabilities;
  private StatementValue totalLiab;
  private StatementValue commonStock;
  private StatementValue retainedEarnings;
  private StatementValue treasuryStock;
  private StatementValue capitalSurplus;
  private StatementValue otherStockholderEquity;
  private StatementValue totalStockholderEquity;
  private StatementValue netTangibleAssets;
}
