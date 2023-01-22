package com.adamnagyan.yahoofinancewebapi.api.v1.mapper;


import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.DividendDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.DividendHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import yahoofinance.histquotes2.HistoricalDividend;

import java.io.IOException;
import java.util.List;

@Mapper
public interface DividendHistoryMapper {

  DividendHistoryMapper INSTANCE = Mappers.getMapper(DividendHistoryMapper.class);


  @Mapping(target = "historicalDividends", source = "dividends")
  DividendHistoryDto toDTO(Integer dummy, List<HistoricalDividend> dividends) throws IOException;

  List<DividendDto> toDividenDtoList(List<HistoricalDividend> dividends);

}
