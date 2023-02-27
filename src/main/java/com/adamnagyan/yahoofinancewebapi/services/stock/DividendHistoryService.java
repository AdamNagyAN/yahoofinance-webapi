package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.DividendHistoryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;

import java.io.IOException;

public interface DividendHistoryService {

	DividendHistoryDto findStockByTicker(String ticker, String timeframe) throws IOException, BadRequestException;

	DividendHistoryDto findStockByTicker(String ticker) throws IOException, BadRequestException;

}
