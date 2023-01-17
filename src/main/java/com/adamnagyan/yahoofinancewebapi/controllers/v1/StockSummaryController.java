package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.summary.StockSummaryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.services.summary.StockSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/symbol/{symbol}")
@RequiredArgsConstructor
public class StockSummaryController {

  private final StockSummaryService stockSummaryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public StockSummaryDto getStockSummaryBySymbol(@PathVariable("symbol") String symbol) throws IOException, BadRequestException {
    return stockSummaryService.getStockSummary(symbol);
  }
}
