package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PriceDto {

	private LocalDate date;

	private double price;

}
