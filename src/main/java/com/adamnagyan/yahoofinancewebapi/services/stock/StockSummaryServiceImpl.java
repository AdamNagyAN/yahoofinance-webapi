package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.StockSummaryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.StockSummaryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
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
