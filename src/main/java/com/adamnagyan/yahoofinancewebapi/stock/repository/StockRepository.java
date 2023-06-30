package com.adamnagyan.yahoofinancewebapi.stock.repository;

import com.adamnagyan.yahoofinancewebapi.stock.model.*;

import java.util.List;

public interface StockRepository {


	public StockQuote getStockQuote(String symbol);
	public List<StockCashflowStatement> getCashflowStatements(String symbol);

	public List<StockCashflowStatement> getCashflowStatements(String symbol, StockFinancialStatementsInterval interval);

	public List<StockIncomeStatement> getIncomeStatements(String symbol);

	public List<StockIncomeStatement> getIncomeStatements(String symbol, StockFinancialStatementsInterval interval);

	public List<StockBalanceSheet> getBalanceSheets(String symbol);

	public List<StockBalanceSheet> getBalanceSheets(String symbol, StockFinancialStatementsInterval interval);

	public List<StockDividend> getDividends(String symbol);

}
