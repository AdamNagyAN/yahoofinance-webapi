package com.adamnagyan.yahoofinancewebapi.api.v1.model.dividend_percentage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DividendPercentageDto {
    private LocalDate date;
    private double dividendPercentage;

}
