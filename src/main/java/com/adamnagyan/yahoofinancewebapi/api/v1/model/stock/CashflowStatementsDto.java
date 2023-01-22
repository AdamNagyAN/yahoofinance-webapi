package com.adamnagyan.yahoofinancewebapi.api.v1.model.stock;

import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatements;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CashflowStatementsDto {
  private List<StockCashflowStatements> cashFlowStatements;
}
