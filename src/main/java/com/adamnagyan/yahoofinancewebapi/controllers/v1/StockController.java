package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.PriceDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.summary.StockSummaryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.services.dividend_percentage.DividendPercentageHistoryService;
import com.adamnagyan.yahoofinancewebapi.services.history.DividendHistoryService;
import com.adamnagyan.yahoofinancewebapi.services.summary.StockSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/symbol/{symbol}")
public class StockController {
  private final DividendHistoryService dividendHistoryService;
  private final DividendPercentageHistoryService dividendPercentageHistoryService;
  private final StockSummaryService stockSummaryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public StockSummaryDto getStockSummaryBySymbol(@PathVariable("symbol") String symbol) throws IOException, BadRequestException {
    return stockSummaryService.getStockSummary(symbol);
  }

  @GetMapping("prices")
  @ResponseStatus(HttpStatus.OK)
  public List<PriceDto> getPriceList(String symbol) throws IOException, BadRequestException {
    return dividendPercentageHistoryService.getPriceHistory(symbol);
  }

  @GetMapping(("dividend-history"))
  @ResponseStatus(HttpStatus.OK)
  public DividendHistoryDto getDividendHistoryBySymbol(@PathVariable("symbol") String symbol, @RequestParam(required = false) String timeFrame) throws IOException, BadRequestException {
    if (timeFrame == null) {
      return dividendHistoryService.findStockByTicker(symbol);
    }
    return dividendHistoryService.findStockByTicker(symbol, timeFrame);
  }

  @GetMapping("dividend-percentage-history")
  @ResponseStatus(HttpStatus.OK)
  public DividendPercentageHistoryDto getDividendPercentageHistory(String symbol) throws IOException, BadRequestException {
    return dividendPercentageHistoryService.getDividendPercentageHistoryDto(symbol);
  }
}
