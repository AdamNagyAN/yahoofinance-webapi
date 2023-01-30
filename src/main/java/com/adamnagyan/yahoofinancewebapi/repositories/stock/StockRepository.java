package com.adamnagyan.yahoofinancewebapi.repositories.stock;

import com.adamnagyan.yahoofinancewebapi.model.stock.StockBalanceSheet;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatement;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockIncomeStatement;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StockRepository {

  public List<StockCashflowStatement> getCashFlowStatements(String symbol) throws JsonProcessingException;

  public List<StockIncomeStatement> getIncomeStatements(String symbol);

  public List<StockBalanceSheet> getBalanceSheets(String symbol);

}
