package com.adamnagyan.yahoofinancewebapi.api.v1.mapper;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.stock.CashflowStatementsDto;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatements;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FinancialStatementsMapper {

  @Mapping(target = "cashFlowStatements", source = "cashFlowStatements")
  public CashflowStatementsDto toCashflowStatementsDto(int dummy, List<StockCashflowStatements> cashFlowStatements);

}
