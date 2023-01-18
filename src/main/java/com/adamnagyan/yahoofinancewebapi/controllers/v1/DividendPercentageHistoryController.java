package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.PriceDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.services.dividend_percentage.DividendPercentageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/symbol/{symbol}/dividend-percentage-history")
public class DividendPercentageHistoryController {

  private final DividendPercentageHistoryService service;

  @GetMapping("/prices")
  @ResponseStatus(HttpStatus.OK)
  public List<PriceDto> getPriceList(String symbol) throws IOException, BadRequestException {
    return service.getPriceHistory(symbol);
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public DividendPercentageHistoryDto getDividendPercentageHistoryDto(String symbol) throws IOException, BadRequestException {
    return service.getDividendPercentageHistoryDto(symbol);
  }
}
