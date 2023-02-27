package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.FinancialStatementsMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.FinancialDataDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.repositories.stock.StockRepository;
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
		Stock stock = YahooFinance.get(symbol);
		if (stock == null) {
			throw new BadRequestException("symbol", "Symbol was not found!");
		}
		return financialStatementsMapper.toFinancialDataDto(0, stockRepository.getIncomeStatements(stock.getSymbol()),
				stockRepository.getCashFlowStatements(stock.getSymbol()),
				stockRepository.getBalanceSheets(stock.getSymbol()));
	}

}
