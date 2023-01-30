package com.adamnagyan.yahoofinancewebapi.api.v1.mapper;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.StockSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockStats;

@Mapper(componentModel = "spring")
public interface StockSummaryMapper {
    StockSummaryMapper INSTANCE = Mappers.getMapper(StockSummaryMapper.class);

    @Mapping(target = "companyName", source = "stock.name")
    @Mapping(target = "symbol", source = "stock.symbol")
    @Mapping(target = "marketCap", source = "stats.marketCap")
    @Mapping(target = "eps", source = "stats.eps")
    @Mapping(target = "pe", source = "stats.pe")
    @Mapping(target = "price", source = "stock.quote.price")
    @Mapping(target = "oneYearTargetPrice", source = "stats.oneYearTargetPrice")
    @Mapping(target = "earningsAnnouncement", source = "stats.earningsAnnouncement")
    @Mapping(target = "annualDividendYield", source = "dividend.annualYield")
    @Mapping(target = "annualDividendYieldPercent", source = "dividend.annualYieldPercent")
    StockSummaryDto stockToStockSummaryDto(Stock stock, StockStats stats, StockDividend dividend);
}
