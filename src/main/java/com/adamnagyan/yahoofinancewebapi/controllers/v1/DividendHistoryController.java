package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendHistoryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.services.history.DividendHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/symbol/{symbol}/dividend-history")
public class DividendHistoryController {
  private final DividendHistoryService dividendHistoryService;

  public DividendHistoryController(DividendHistoryService dividendHistoryService) {
    this.dividendHistoryService = dividendHistoryService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public DividendHistoryDto getDividendHistoryBySymbol(@PathVariable("symbol") String symbol, @RequestParam(required = false) String timeFrame) throws IOException, BadRequestException {
    if (timeFrame == null) {
      return dividendHistoryService.findStockByTicker(symbol);
    }
    return dividendHistoryService.findStockByTicker(symbol, timeFrame);
  }
}
