package com.adamnagyan.yahoofinancewebapi.services.history;

import com.adamnagyan.yahoofinancewebapi.api.v1.mapper.history.DividendHistoryMapper;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendHistoryDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.model.history.DividendHistoryTimeFrames;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DividendHistoryServiceImpl implements DividendHistoryService {

  private final DividendHistoryMapper dividendHistoryMapper;

  @Override
  public DividendHistoryDto findStockByTicker(String ticker, String timeframe) throws IOException, BadRequestException {
    Calendar cal = Calendar.getInstance();
    if (DividendHistoryTimeFrames.getTimeFrameByName(timeframe) == null) {
      throw new BadRequestException("timeFrame");
    }
    cal.add(Calendar.YEAR, DividendHistoryTimeFrames.getTimeFrameByName(timeframe).getValue());

    Stock stock = YahooFinance.get(ticker, cal);
    if (stock == null) {
      throw new BadRequestException("ticker");
    }

    DividendHistoryDto result = dividendHistoryMapper.toDTO(stock, cal);
    result.setValidTimeFrames(
            Arrays.stream(DividendHistoryTimeFrames.values())
                    .map(DividendHistoryTimeFrames::getName)
                    .collect(Collectors.toList()));
    return result;
  }

  @Override
  public DividendHistoryDto findStockByTicker(String ticker) throws IOException, BadRequestException {
    return this.findStockByTicker(ticker, "max");
  }
}
