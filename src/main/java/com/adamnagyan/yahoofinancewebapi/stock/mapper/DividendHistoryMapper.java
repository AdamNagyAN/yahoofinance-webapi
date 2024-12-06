package com.adamnagyan.yahoofinancewebapi.stock.mapper;

import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.DividendHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import yahoofinance.histquotes2.HistoricalDividend;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Mapper
public interface DividendHistoryMapper {

	DividendHistoryMapper INSTANCE = Mappers.getMapper(DividendHistoryMapper.class);

	@Mapping(target = "historicalDividends", source = "dividends")
	@Mapping(target = "validTimeFrames", source = "validTimeFrames")
	@Mapping(target = "divGrowthRates", source = "divGrowthRates")
	DividendHistoryDto toDTO(Integer dummy, List<DividendDto> dividends, List<String> validTimeFrames,
			Map<String, Double> divGrowthRates) throws IOException;

	List<DividendDto> toDividenDtoList(List<HistoricalDividend> dividends);

}
