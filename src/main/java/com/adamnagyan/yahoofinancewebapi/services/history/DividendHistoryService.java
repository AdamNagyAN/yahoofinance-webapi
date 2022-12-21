package com.adamnagyan.yahoofinancewebapi.services.history;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendHistoryDto;

import java.io.IOException;

public interface DividendHistoryService {
  DividendHistoryDto findStockByTicker(String ticker, String timeframe) throws IOException;

  DividendHistoryDto findStockByTicker(String ticker) throws IOException;
}
