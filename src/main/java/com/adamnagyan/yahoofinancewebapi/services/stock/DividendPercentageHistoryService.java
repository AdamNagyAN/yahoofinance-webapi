package com.adamnagyan.yahoofinancewebapi.services.stock;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.PriceDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.BadRequestException;

import java.io.IOException;
import java.util.List;

public interface DividendPercentageHistoryService {

	public List<PriceDto> getPriceHistory(String stock) throws IOException, BadRequestException;

	public DividendPercentageHistoryDto getDividendPercentageHistoryDto(String symbol, String timeframe)
			throws IOException, BadRequestException;

}
