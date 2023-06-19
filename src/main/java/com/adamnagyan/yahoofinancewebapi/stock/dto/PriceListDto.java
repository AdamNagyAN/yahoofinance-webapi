package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PriceListDto {

	private List<PriceDto> historicalPrices;

}
