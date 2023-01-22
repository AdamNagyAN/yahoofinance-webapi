package com.adamnagyan.yahoofinancewebapi.repositories.stock;

import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatements;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StockRepository {

  public List<StockCashflowStatements> getCashFlowStatements(String symbol) throws JsonProcessingException;

}
