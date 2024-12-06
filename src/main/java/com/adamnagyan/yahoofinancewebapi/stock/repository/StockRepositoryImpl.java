package com.adamnagyan.yahoofinancewebapi.stock.repository;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.BaseAppExceptionFactory;
import com.adamnagyan.yahoofinancewebapi.stock.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.*;

@Repository
@Slf4j
public class StockRepositoryImpl implements StockRepository {
	public static final String STOCK_QUOTE = "/optionChain/result/0/quote";
	public static final String YH_FINANCE_ERROR_DESCRIPTION_PATH = "/quoteSummary/error/description";

	public static final String CASHFLOW_STATEMENT_YEARLY_JSON_PATH = "/quoteSummary/result/0/cashflowStatementHistory/cashflowStatements";

	public static final String CASHFLOW_STATEMENT_QUARTERLY_JSON_PATH = "/quoteSummary/result/0/cashflowStatementHistoryQuarterly/cashflowStatements";

	public static final String INCOME_STATEMENT_YEARLY_JSON_PATH = "/quoteSummary/result/0/incomeStatementHistory/incomeStatementHistory";

	public static final String INCOME_STATEMENT_QUARTERLY_JSON_PATH = "/quoteSummary/result/0/incomeStatementHistoryQuarterly/incomeStatementHistory";

	public static final String BALANCE_SHEET_YEARLY_JSON_PATH = "/quoteSummary/result/0/balanceSheetHistory/balanceSheetStatements";

	public static final String BALANCE_SHEET_QUARTERLY_JSON_PATH = "/quoteSummary/result/0/balanceSheetHistoryQuarterly/balanceSheetStatements";

	private final RestTemplate restTemplate;

	private final ObjectMapper objectMapper;

	@Value("${external-apis.yahoofinance-cashflow}")
	private String cashFlowStatementApi;

	@Value("${external-apis.yahoofinance-cashflow-quarterly}")
	private String cashFlowStatementQuarterlyApi;

	@Value("${external-apis.yahoofinance-income-statements}")
	private String incomeStatementsApi;

	@Value("${external-apis.yahoofinance-income-statements-quarterly}")
	private String incomeStatementsQuarterlyApi;

	@Value("${external-apis.yahoofinance-balance-sheet}")
	private String balanceSheetApi;

	@Value("${external-apis.yahoofinance-balance-sheet-quarterly}")
	private String balanceSheetQuarterlyApi;

	@Value("${external-apis.yahoofinance-quote}")
	private String quoteApi;

	@Value("${external-apis.yahoofinance-dividend_history}")
	private String dividendHistoryApi;

	public StockRepositoryImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.restTemplate = restTemplateBuilder.errorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		}).build();
	}

	@Override
	public StockQuote getStockQuote(String symbol) {
		String requestUrl = UriComponentsBuilder.fromHttpUrl(quoteApi).buildAndExpand(symbol).toUriString();
		log.info("Requesting statements from {}", requestUrl);
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(requestUrl, JsonNode.class);
		if (!response.getStatusCode().is2xxSuccessful()) {
			Optional<String> description = Optional
							.ofNullable(response.getBody().findPath(YH_FINANCE_ERROR_DESCRIPTION_PATH).asText());
			if (description.isEmpty()) {
				BaseAppExceptionFactory.externalService();
			}
			BaseAppExceptionFactory.externalService(description.get(), response.getStatusCodeValue());
		}
		log.info("Response: {}", response.getBody());
		JsonNode body = response.getBody().at(STOCK_QUOTE);
		if(body.isEmpty()){
			return null;
		}
		StockQuote stockQuote = objectMapper.convertValue(body, StockQuote.class);
		return stockQuote;
	}

	@Override
	public List<StockCashflowStatement> getCashflowStatements(String symbol) {
		return this.getCashflowStatements(symbol, StockFinancialStatementsInterval.YEARLY);
	}

	@Override
	public List<StockCashflowStatement> getCashflowStatements(String symbol,
			StockFinancialStatementsInterval interval) {
		if (interval == StockFinancialStatementsInterval.QUARTERLY) {
			return getStatements(symbol, cashFlowStatementQuarterlyApi, CASHFLOW_STATEMENT_QUARTERLY_JSON_PATH,
					StockCashflowStatement.class);
		}
		return getStatements(symbol, cashFlowStatementApi, CASHFLOW_STATEMENT_YEARLY_JSON_PATH,
				StockCashflowStatement.class);
	}

	@Override
	public List<StockBalanceSheet> getBalanceSheets(String symbol) {
		return getBalanceSheets(symbol, StockFinancialStatementsInterval.YEARLY);
	}

	@Override
	public List<StockBalanceSheet> getBalanceSheets(String symbol, StockFinancialStatementsInterval interval) {
		if (interval == StockFinancialStatementsInterval.QUARTERLY) {
			return getStatements(symbol, balanceSheetQuarterlyApi, BALANCE_SHEET_QUARTERLY_JSON_PATH,
					StockBalanceSheet.class);
		}
		return getStatements(symbol, balanceSheetApi, BALANCE_SHEET_YEARLY_JSON_PATH, StockBalanceSheet.class);
	}

	@Override
	public List<StockIncomeStatement> getIncomeStatements(String symbol) {
		return getIncomeStatements(symbol, StockFinancialStatementsInterval.YEARLY);
	}

	@Override
	public List<StockIncomeStatement> getIncomeStatements(String symbol, StockFinancialStatementsInterval interval) {
		if (interval == StockFinancialStatementsInterval.QUARTERLY) {
			return getStatements(symbol, incomeStatementsQuarterlyApi, INCOME_STATEMENT_QUARTERLY_JSON_PATH,
					StockIncomeStatement.class);
		}
		return getStatements(symbol, incomeStatementsApi, INCOME_STATEMENT_YEARLY_JSON_PATH,
				StockIncomeStatement.class);
	}

	private <T> List<T> getStatements(String symbol, String apiUrl, String jsonPath, Class<T> clazz) {
		String requestUrl = UriComponentsBuilder.fromHttpUrl(apiUrl).buildAndExpand(symbol).toUriString();
		log.info("Requesting statements from {}", requestUrl);
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(requestUrl, JsonNode.class);
		if (!response.getStatusCode().is2xxSuccessful()) {
			Optional<String> description = Optional
				.ofNullable(response.getBody().findPath(YH_FINANCE_ERROR_DESCRIPTION_PATH).asText());
			if (description.isEmpty()) {
				BaseAppExceptionFactory.externalService();
			}
			BaseAppExceptionFactory.externalService(description.get(), response.getStatusCodeValue());
		}
		log.info("Response: {}", response.getBody());
		Iterator<JsonNode> body = response.getBody().at(jsonPath).elements();

		List<T> result = new ArrayList<>();
		body.forEachRemaining(item -> result.add(objectMapper.convertValue(item, clazz)));
		log.info("JSON {} : {}", jsonPath, result);

		return result;
	}

	@SneakyThrows
	@Override
	public List<StockDividend> getDividends(String symbol) {
		URL requestUrl = UriComponentsBuilder.fromHttpUrl(dividendHistoryApi).buildAndExpand(symbol, 0, 9999999999L, "1d").toUri().toURL();
		InputStreamReader inputStreamReader = new InputStreamReader(requestUrl.openConnection().getInputStream());

		BufferedReader br = new BufferedReader(inputStreamReader);
		String line = "";
		String cvsSlitBy = ",";

		while ((line = br.readLine()) != null) {
			String[] dividend = line.split(cvsSlitBy);
			System.out.println(Arrays.stream(dividend).toList());
		}


		log.info("Requesting dividend history from {}", requestUrl);
		return null;
	}
}
