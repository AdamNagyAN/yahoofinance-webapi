package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockQuote;

import java.io.IOException;

public interface StockSummaryService {

	StockQuote getStockSummary(String symbol) throws IOException, BadRequestException;

}
