package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.*;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.services.stock.DividendHistoryService;
import com.adamnagyan.yahoofinancewebapi.services.stock.DividendPercentageHistoryService;
import com.adamnagyan.yahoofinancewebapi.services.stock.FinancialStatementsService;
import com.adamnagyan.yahoofinancewebapi.services.stock.StockSummaryService;
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
  private final FinancialStatementsService financialStatementsService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public StockSummaryDto getStockSummaryBySymbol(@PathVariable("symbol") String symbol) throws IOException, BadRequestException {
    return stockSummaryService.getStockSummary(symbol);
  }

  @GetMapping("prices")
  @ResponseStatus(HttpStatus.OK)
  public List<PriceDto> getPriceList(@PathVariable("symbol") String symbol) throws IOException, BadRequestException {
    return dividendPercentageHistoryService.getPriceHistory(symbol);
  }

  @GetMapping("dividend-history")
  @ResponseStatus(HttpStatus.OK)
  public DividendHistoryDto getDividendHistoryBySymbol(@PathVariable("symbol") String symbol, @RequestParam(required = false) String timeFrame) throws IOException, BadRequestException {
    if (timeFrame == null) {
      return dividendHistoryService.findStockByTicker(symbol);
    }
    return dividendHistoryService.findStockByTicker(symbol, timeFrame);
  }

  @GetMapping("dividend-percentage-history")
  @ResponseStatus(HttpStatus.OK)
  public DividendPercentageHistoryDto getDividendPercentageHistory(@PathVariable("symbol") String symbol, @RequestParam(required = false) String timeFrame) throws IOException, BadRequestException {
    if (timeFrame == null) {
      return dividendPercentageHistoryService.getDividendPercentageHistoryDto(symbol, "max");
    }
    return dividendPercentageHistoryService.getDividendPercentageHistoryDto(symbol, timeFrame);
  }

  @GetMapping("financials")
  @ResponseStatus(HttpStatus.OK)
  public FinancialDataDto getCashFlowStatement(@PathVariable("symbol") String symbol) {
    return financialStatementsService.getFinancialData(symbol);
  }
}
