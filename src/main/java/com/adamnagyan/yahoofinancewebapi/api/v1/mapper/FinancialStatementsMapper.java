package com.adamnagyan.yahoofinancewebapi.api.v1.mapper;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.CashflowStatementDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.FinancialDataDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.IncomeStatementDto;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatement;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockIncomeStatement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FinancialStatementsMapper {

  @Mapping(target = "incomeStatements", source = "incomeStatements")
  @Mapping(target = "cashFlowStatements", source = "cashFlowStatements")
  FinancialDataDto toFinancialDataDto(int dummy, List<StockIncomeStatement> incomeStatements, List<StockCashflowStatement> cashFlowStatements);

  //  @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd")
//  public BalanceSheetDto toBalanceSheetDto(StockBalanceSheet stockBalanceSheet);

  List<IncomeStatementDto> toIncomeStatementListDto(List<StockIncomeStatement> incomeStatements);

  List<CashflowStatementDto> toCashflowStatementListDto(List<StockCashflowStatement> cashFlowStatements);

  IncomeStatementDto toIncomeStatementDto(StockIncomeStatement incomeStatement);

  CashflowStatementDto toCashflowStatementDto(StockCashflowStatement cashFlowStatement);
}
