package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.dto.FinancialDataDto;
import com.adamnagyan.yahoofinancewebapi.stock.mapper.FinancialStatementsMapper;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockQuote;
import com.adamnagyan.yahoofinancewebapi.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Service
@RequiredArgsConstructor
public class FinancialStatementsServiceImpl implements FinancialStatementsService {

	private final StockRepository stockRepository;

	private final FinancialStatementsMapper financialStatementsMapper;

	@Override
	@SneakyThrows
	public FinancialDataDto getFinancialData(String symbol) {
		StockQuote stock = stockRepository.getStockQuote(symbol);
		if (stock == null) {
			throw new BadRequestException("symbol", "Symbol was not found!");
		}
		return financialStatementsMapper.toFinancialDataDto(0, stockRepository.getIncomeStatements(stock.getSymbol()),
				stockRepository.getCashflowStatements(stock.getSymbol()),
				stockRepository.getBalanceSheets(stock.getSymbol()));
	}

}
