package com.adamnagyan.yahoofinancewebapi.api.v1.mapper;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.StockHistoryItemDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.model.user_stock.StockHistoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface StockHistoryMapper {

	@Mapping(target = "fullPrice",
			expression = "java(stockHistoryItem.getPrice() * stockHistoryItem.getQuantity() + stockHistoryItem.getTransactionFee())")
	StockHistoryItemDto toStockHistoryItemDto(StockHistoryItem stockHistoryItem);

	StockHistoryItem toStockHistoryItem(UserStockHistoryItemRequestDto dto);

	@Mapping(target = "stockHistoryItems", source = "stockHistoryItems")
	StockHistoryListDto toStockHistoryList(int dummy, List<StockHistoryItem> stockHistoryItems);

}
