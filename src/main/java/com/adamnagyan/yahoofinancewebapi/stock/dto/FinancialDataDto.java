package com.adamnagyan.yahoofinancewebapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialDataDto {

	private List<IncomeStatementDto> incomeStatements;

	private List<BalanceSheetDto> balanceSheets;

	private List<CashflowStatementDto> cashFlowStatements;

}
