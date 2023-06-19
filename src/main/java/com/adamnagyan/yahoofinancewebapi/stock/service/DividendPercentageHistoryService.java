package com.adamnagyan.yahoofinancewebapi.stock.service;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BadRequestException;
import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.PriceDto;

import java.io.IOException;
import java.util.List;

public interface DividendPercentageHistoryService {

	public List<PriceDto> getPriceHistory(String stock) throws IOException, BadRequestException;

	public DividendPercentageHistoryDto getDividendPercentageHistoryDto(String symbol, String timeframe)
			throws IOException, BadRequestException;

}
