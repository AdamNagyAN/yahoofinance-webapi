package com.adamnagyan.yahoofinancewebapi.services.dividend_percentage;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage.PriceDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;

import java.io.IOException;
import java.util.List;

public interface DividendPercentageHistoryService {

  public List<PriceDto> getPriceHistory(String stock) throws IOException, BadRequestException;

  public DividendPercentageHistoryDto getDividendPercentageHistoryDto(String symbol) throws IOException, BadRequestException;

}
