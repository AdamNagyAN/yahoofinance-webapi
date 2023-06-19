package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.dto.StockSummaryDto;
import com.adamnagyan.yahoofinancewebapi.stock.mapper.StockSummaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StockSummaryServiceImpl implements StockSummaryService {

	private final StockSummaryMapper stockSummaryMapper;

	@Override
	public StockSummaryDto getStockSummary(String symbol) throws IOException, BadRequestException {
		Stock stock = YahooFinance.get(symbol);
		if (stock == null) {
			throw new BadRequestException("symbol", "Symbol was not found");
		}
		return stockSummaryMapper.stockToStockSummaryDto(stock, stock.getStats(true), stock.getDividend());
	}

}
