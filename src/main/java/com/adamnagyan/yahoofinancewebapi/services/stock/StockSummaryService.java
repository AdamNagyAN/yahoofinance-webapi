package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.StockSummaryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;

import java.io.IOException;

public interface StockSummaryService {

	StockSummaryDto getStockSummary(String symbol) throws IOException, BadRequestException;

}
