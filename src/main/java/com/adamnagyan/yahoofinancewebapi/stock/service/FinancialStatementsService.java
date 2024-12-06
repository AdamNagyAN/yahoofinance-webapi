package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.stock.dto.FinancialDataDto;

public interface FinancialStatementsService {

	public FinancialDataDto getFinancialData(String stock);

}
