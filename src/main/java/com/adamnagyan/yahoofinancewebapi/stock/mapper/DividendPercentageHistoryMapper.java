package com.adamnagyan.yahoofinancewebapi.stock.mapper;

import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendPercentageDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendPercentageHistoryDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import yahoofinance.histquotes.HistoricalQuote;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DividendPercentageHistoryMapper {

	DividendPercentageHistoryMapper INSTANCE = Mappers.getMapper(DividendPercentageHistoryMapper.class);

	@Mapping(target = "historicalDividendPercentages", source = "dividendPercentageDtoList")
	@Mapping(target = "currentDividendPercentage", source = "currentDividendYield")
	@Mapping(target = "averageDividendPercentage", source = "averageDividendYield")
	DividendPercentageHistoryDto toDividendPercentageHistoryDto(List<DividendPercentageDto> dividendPercentageDtoList,
			double currentDividendYield, double averageDividendYield, List<String> validTimeFrames);

	DividendPercentageDto toDividendPercentageDto(double dividendPercentage, LocalDate date);

	List<PriceDto> toPriceListDto(List<HistoricalQuote> historicalPrices);

	@Mapping(source = "close", target = "price")
	@Mapping(source = "date", target = "date")
	PriceDto toPriceDto(HistoricalQuote historicalQuote);

}
