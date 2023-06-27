package com.adamnagyan.yahoofinancewebapi.stock.mapper;

import com.adamnagyan.yahoofinancewebapi.stock.dto.BalanceSheetDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.CashflowStatementDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.FinancialDataDto;
import com.adamnagyan.yahoofinancewebapi.stock.dto.IncomeStatementDto;
import com.adamnagyan.yahoofinancewebapi.stock.model.StatementValue;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockBalanceSheet;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockCashflowStatement;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockIncomeStatement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(imports = { java.time.Instant.class, java.time.ZoneId.class })
public interface FinancialStatementsMapper {

	@Mapping(target = "incomeStatements", source = "incomeStatements")
	@Mapping(target = "cashFlowStatements", source = "cashFlowStatements")
	@Mapping(target = "balanceSheets", source = "balanceSheets")
	FinancialDataDto toFinancialDataDto(int dummy, List<StockIncomeStatement> incomeStatements,
			List<StockCashflowStatement> cashFlowStatements, List<StockBalanceSheet> balanceSheets);

	// @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd"
	@Mapping(target = "endDate",
			expression = "java(Instant.ofEpochMilli(stockBalanceSheet.getEndDate().getRaw() * 1000).atZone(ZoneId.systemDefault()).toLocalDate())")
	@Mapping(target = "cash", source = "cash.raw")
	@Mapping(target = "shortTermInvestments", source = "shortTermInvestments.raw")
	@Mapping(target = "netReceivables", source = "netReceivables.raw")
	@Mapping(target = "inventory", source = "inventory.raw")
	@Mapping(target = "otherCurrentAssets", source = "otherCurrentAssets.raw")
	@Mapping(target = "totalCurrentAssets", source = "totalCurrentAssets.raw")
	@Mapping(target = "longTermInvestments", source = "longTermInvestments.raw")
	@Mapping(target = "propertyPlantEquipment", source = "propertyPlantEquipment.raw")
	@Mapping(target = "goodWill", source = "goodWill.raw")
	@Mapping(target = "otherAssets", source = "otherAssets.raw")
	@Mapping(target = "deferredLongTermAssetCharges", source = "deferredLongTermAssetCharges.raw")
	@Mapping(target = "totalAssets", source = "totalAssets.raw")
	@Mapping(target = "accountsPayable", source = "accountsPayable.raw")
	@Mapping(target = "shortLongTermDebt", source = "shortLongTermDebt.raw")
	@Mapping(target = "otherCurrentLiab", source = "otherCurrentLiab.raw")
	@Mapping(target = "longTermDebt", source = "longTermDebt.raw")
	@Mapping(target = "otherLiab", source = "otherLiab.raw")
	@Mapping(target = "deferredLongTermLiab", source = "deferredLongTermLiab.raw")
	@Mapping(target = "minorityInterest", source = "minorityInterest.raw")
	@Mapping(target = "totalCurrentLiabilities", source = "totalCurrentLiabilities.raw")
	@Mapping(target = "totalLiab", source = "totalLiab.raw")
	@Mapping(target = "commonStock", source = "commonStock.raw")
	@Mapping(target = "retainedEarnings", source = "retainedEarnings.raw")
	@Mapping(target = "treasuryStock", source = "treasuryStock.raw")
	@Mapping(target = "capitalSurplus", source = "capitalSurplus.raw")
	@Mapping(target = "otherStockholderEquity", source = "otherStockholderEquity.raw")
	@Mapping(target = "totalStockholderEquity", source = "totalStockholderEquity.raw")
	@Mapping(target = "netTangibleAssets", source = "netTangibleAssets.raw")
	BalanceSheetDto toBalanceSheetDto(StockBalanceSheet stockBalanceSheet);

	List<IncomeStatementDto> toIncomeStatementListDto(List<StockIncomeStatement> incomeStatements);

	List<CashflowStatementDto> toCashflowStatementListDto(List<StockCashflowStatement> cashFlowStatements);

	@Mapping(target = "endDate",
			expression = "java(Instant.ofEpochMilli(incomeStatement.getEndDate().getRaw() * 1000).atZone(ZoneId.systemDefault()).toLocalDate())")
	IncomeStatementDto toIncomeStatementDto(StockIncomeStatement incomeStatement);

	@Mapping(target = "endDate",
			expression = "java(Instant.ofEpochMilli(cashFlowStatement.getEndDate().getRaw() * 1000).atZone(ZoneId.systemDefault()).toLocalDate())")
	CashflowStatementDto toCashflowStatementDto(StockCashflowStatement cashFlowStatement);

	default Long map(StatementValue value) {
		return value.getRaw();
	}

}
