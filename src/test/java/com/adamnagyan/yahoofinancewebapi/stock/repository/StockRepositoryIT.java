package com.adamnagyan.yahoofinancewebapi.stock.repository;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BaseAppException;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockBalanceSheet;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockCashflowStatement;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockFinancialStatementsInterval;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockIncomeStatement;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class StockRepositoryIT {

	@Autowired
	private StockRepositoryImpl stockRepositoryImpl;

	@Test
	public void getStockCashFlowStatements() {
		List<StockCashflowStatement> resultYearly = stockRepositoryImpl.getCashflowStatements("CVX",
				StockFinancialStatementsInterval.YEARLY);
		Assertions.assertEquals(4, resultYearly.size());
		List<StockCashflowStatement> resultQuarterly = stockRepositoryImpl.getCashflowStatements("CVX",
				StockFinancialStatementsInterval.QUARTERLY);
		Assertions.assertEquals(4, resultQuarterly.size());
	}

	@Test(expected = BaseAppException.class)
	public void getStockCashFlowStatementsWithInvalidStock() {
		stockRepositoryImpl.getCashflowStatements("asdasdasd", StockFinancialStatementsInterval.YEARLY);
	}

	@Test
	public void getBalanceSheet() {
		List<StockBalanceSheet> resultYearly = stockRepositoryImpl.getBalanceSheets("CVX",
				StockFinancialStatementsInterval.YEARLY);
		Assertions.assertEquals(4, resultYearly.size());
		List<StockBalanceSheet> resultQuarterly = stockRepositoryImpl.getBalanceSheets("CVX",
				StockFinancialStatementsInterval.QUARTERLY);
		Assertions.assertEquals(4, resultQuarterly.size());
	}

	@Test(expected = BaseAppException.class)
	public void getBalanceSheetsWithInvalidStock() {
		stockRepositoryImpl.getCashflowStatements("asdasdasd", StockFinancialStatementsInterval.YEARLY);
	}

	@Test
	public void getIncomeStatements() {
		List<StockIncomeStatement> resultYearly = stockRepositoryImpl.getIncomeStatements("CVX",
				StockFinancialStatementsInterval.YEARLY);
		Assertions.assertEquals(4, resultYearly.size());
		List<StockIncomeStatement> resultQuarterly = stockRepositoryImpl.getIncomeStatements("CVX",
				StockFinancialStatementsInterval.QUARTERLY);
		Assertions.assertEquals(4, resultQuarterly.size());
	}

	@Test(expected = BaseAppException.class)
	public void getIncomeStatementsWithInvalidStock() {
		stockRepositoryImpl.getCashflowStatements("asdasdasd", StockFinancialStatementsInterval.YEARLY);
	}

}