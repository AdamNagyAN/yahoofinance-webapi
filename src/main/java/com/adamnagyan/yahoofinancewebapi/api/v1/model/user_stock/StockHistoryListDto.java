package com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock;

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
