package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.model.history.DividendHistory;
import com.adamnagyan.yahoofinancewebapi.services.history.DividendHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/symbol/{symbol}/dividend-history")
public class DividendHistoryController {
  private final DividendHistoryService dividendHistoryService;

  public DividendHistoryController(DividendHistoryService dividendHistoryService) {
    this.dividendHistoryService = dividendHistoryService;
  }

  @GetMapping
  public DividendHistory getDividendHistoryBySymbol(@PathVariable("symbol") String symbol){
    return dividendHistoryService.findStockByTicker(symbol);
  }
}
