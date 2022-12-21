package com.adamnagyan.yahoofinancewebapi.services.history;

import com.adamnagyan.yahoofinancewebapi.model.history.DividendHistory;

public interface DividendHistoryService {
  DividendHistory findStockByTicker(String ticker);
}
