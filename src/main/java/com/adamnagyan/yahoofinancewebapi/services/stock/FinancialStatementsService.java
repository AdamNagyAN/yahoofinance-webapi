package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.CashflowStatementsDto;

public interface FinancialStatementsService {
  public CashflowStatementsDto getStockCashFlowStatement(String stock);
}
