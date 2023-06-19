package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.dto.StockSummaryDto;

import java.io.IOException;

public interface StockSummaryService {

	StockSummaryDto getStockSummary(String symbol) throws IOException, BadRequestException;

}
