package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.FinancialDataDto;

public interface FinancialStatementsService {
  public FinancialDataDto getFinancialData(String stock);

}
