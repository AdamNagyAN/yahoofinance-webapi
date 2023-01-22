package com.adamnagyan.yahoofinancewebapi.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockCashflowStatements {
  private Date endDate;
  private Long netIncome;
  private Long depreciation;
  private Long changeToNetincome;
  private Long changeToAccountReceivables;
  private Long changeToLiabilities;
  private Long changeToInventory;
  private Long changeToOperatingActivities;
  private Long totalCashFromOperatingActivities;
  private Long capitalExpenditures;
  private Long investments;
  private Long otherCashflowsFromInvestingActivities;
  private Long totalCashflowsFromInvestingActivities;
  private Long dividendsPaid;
  private Long netBorrowings;
  private Long otherCashflowsFromFinancingActivities;
  private Long totalCashFromFinancingActivities;
  private Long effectOfExchangeRate;
  private Long changeInCash;
  private Long issuanceOfStock;
  private Long freeCashFlow;
}
