package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendHistoryDto;

import java.io.IOException;

public interface DividendHistoryService {

	DividendHistoryDto findStockByTicker(String ticker, String timeframe) throws IOException, BadRequestException;

	DividendHistoryDto findStockByTicker(String ticker) throws IOException, BadRequestException;

}
