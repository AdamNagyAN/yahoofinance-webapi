package com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryItemDto {

	private Long id;

	private String symbol;

	private Double quantity;

	private Double transactionFee;

	private Double price;

	private Double fullPrice;

	private LocalDate buyDate;

}
