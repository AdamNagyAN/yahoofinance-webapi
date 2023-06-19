package com.adamnagyan.yahoofinancewebapi.user_stock.mapper;

import com.adamnagyan.yahoofinancewebapi.user_stock.dto.StockHistoryItemDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.StockHistoryListDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.dto.UserStockHistoryItemRequestDto;
import com.adamnagyan.yahoofinancewebapi.user_stock.model.StockHistoryItem;
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
