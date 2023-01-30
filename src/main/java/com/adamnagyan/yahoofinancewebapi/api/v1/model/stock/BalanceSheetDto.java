package com.adamnagyan.yahoofinancewebapi.api.v1.model.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BalanceSheetDto {
  private LocalDate endDate;
  private Long cash;
  private Long shortTermInvestments;
  private Long netReceivables;
  private Long inventory;
  private Long otherCurrentAssets;
  private Long totalCurrentAssets;
  private Long longTermInvestments;
  private Long propertyPlantEquipment;
  private Long goodWill;
  private Long otherAssets;
  private Long deferredLongTermAssetCharges;
  private Long totalAssets;
  private Long accountsPayable;
  private Long shortLongTermDebt;
  private Long otherCurrentLiab;
  private Long longTermDebt;
  private Long otherLiab;
  private Long deferredLongTermLiab;
  private Long minorityInterest;
  private Long totalCurrentLiabilities;
  private Long totalLiab;
  private Long commonStock;
  private Long retainedEarnings;
  private Long treasuryStock;
  private Long capitalSurplus;
  private Long otherStockholderEquity;
  private Long totalStockholderEquity;
  private Long netTangibleAssets;
}
