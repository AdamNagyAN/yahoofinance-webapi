package com.adamnagyan.yahoofinancewebapi.api.v1.model.user_stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStockHistoryItemRequestDto {

	private String symbol;

	@NotNull
	private Double quantity;

	private Double transactionFee = 0.0;

	@NotNull
	private Double price;

	private LocalDate buyDate = LocalDate.now();

}
