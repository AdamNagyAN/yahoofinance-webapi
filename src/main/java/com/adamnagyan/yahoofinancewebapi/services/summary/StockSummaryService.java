package com.adamnagyan.yahoofinancewebapi.services.summary;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.summary.StockSummaryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;

import java.io.IOException;

public interface StockSummaryService {
  StockSummaryDto getStockSummary(String symbol) throws IOException, BadRequestException;
}
