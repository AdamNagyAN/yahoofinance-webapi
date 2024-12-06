package com.adamnagyan.yahoofinancewebapi.user_stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryListDto {

	List<StockHistoryItemDto> stockHistoryItems;

}
