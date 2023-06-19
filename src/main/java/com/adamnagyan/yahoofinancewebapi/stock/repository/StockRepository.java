package com.adamnagyan.yahoofinancewebapi.stock.repository;

import com.adamnagyan.yahoofinancewebapi.stock.model.StockBalanceSheet;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockCashflowStatement;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockIncomeStatement;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StockRepository {

	public List<StockCashflowStatement> getCashFlowStatements(String symbol) throws JsonProcessingException;

	public List<StockIncomeStatement> getIncomeStatements(String symbol);

	public List<StockBalanceSheet> getBalanceSheets(String symbol);

}
