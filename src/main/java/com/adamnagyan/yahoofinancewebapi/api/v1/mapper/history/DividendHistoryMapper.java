package com.adamnagyan.yahoofinancewebapi.api.v1.mapper.history;


import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.history.DividendHistoryDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import yahoofinance.Stock;
import yahoofinance.histquotes2.HistoricalDividend;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Mapper
public interface DividendHistoryMapper {

  DividendHistoryMapper INSTANCE = Mappers.getMapper(DividendHistoryMapper.class);


  @Mapping(target = "symbol", source = "symbol")
  @Mapping(target = "companyName", source = "name")
  @Mapping(target = "historicalDividends", expression = "java(toDividendDTO(stock.getDividendHistory(cal)))")
  DividendHistoryDto toDTO(Stock stock, @Context Calendar cal) throws IOException;

  List<DividendDto> toDividendDTO(List<HistoricalDividend> dividends);

}
