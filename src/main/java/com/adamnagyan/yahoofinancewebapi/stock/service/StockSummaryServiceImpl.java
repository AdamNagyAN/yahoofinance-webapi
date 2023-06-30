package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.mapper.StockSummaryMapper;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockQuote;
import com.adamnagyan.yahoofinancewebapi.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StockSummaryServiceImpl implements StockSummaryService {

	private final StockSummaryMapper stockSummaryMapper;
	private final StockRepository stockRepository;

	@Override
	public StockQuote getStockSummary(String symbol) throws IOException, BadRequestException {
		StockQuote stock = stockRepository.getStockQuote(symbol);
		if (stock == null) {
			throw new BadRequestException("symbol", "Symbol was not found");
		}
		return stock;
	}

}
