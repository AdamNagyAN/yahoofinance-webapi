package com.adamnagyan.yahoofinancewebapi.stock.repository;

import com.adamnagyan.yahoofinancewebapi.stock.model.StockBalanceSheet;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockCashflowStatement;
import com.adamnagyan.yahoofinancewebapi.stock.model.StockIncomeStatement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

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

  @Override
  @SneakyThrows
  public List<StockCashflowStatement> getCashFlowStatements(String symbol) {
    String annualRequestUrl = UriComponentsBuilder.fromHttpUrl(cashFlowStatementApi)
            .buildAndExpand(symbol)
            .toUriString();
    String quarterlyRequestUrl = UriComponentsBuilder.fromHttpUrl(cashFlowStatementQuarterlyApi)
            .buildAndExpand(symbol)
            .toUriString();
    ResponseEntity<String> annualResponse = restTemplate.getForEntity(annualRequestUrl, String.class);
    ResponseEntity<String> quarterlyResponse = restTemplate.getForEntity(quarterlyRequestUrl, String.class);

    if ((annualResponse.getStatusCode() == HttpStatus.OK) && quarterlyResponse.getStatusCode() == HttpStatus.OK) {
      JsonNode annualJson = objectMapper.readTree(annualResponse.getBody());
      JsonNode quarterlyJson = objectMapper.readTree(quarterlyResponse.getBody());
      ArrayNode cashFlowStatements = (ArrayNode) annualJson.get("quoteSummary")
              .get("result")
              .get(0)
              .get("cashflowStatementHistory")
              .get("cashflowStatements");
      ArrayNode quarterlyCashFlowStatements = (ArrayNode) quarterlyJson.get("quoteSummary")
              .get("result")
              .get(0)
              .get("cashflowStatementHistoryQuarterly")
              .get("cashflowStatements");

      Optional<StockCashflowStatement> ttmCashFlowStatements = parseCashflowStatementsFromJson(
              quarterlyCashFlowStatements)
              .stream()
              .reduce(StockCashflowStatement::add);

      List<StockCashflowStatement> result = parseCashflowStatementsFromJson(cashFlowStatements);
      ttmCashFlowStatements.ifPresent(stockCashflowStatements -> result.add(0, stockCashflowStatements));
      return result;
    }
    return new ArrayList<>();
  }

  private List<StockCashflowStatement> parseCashflowStatementsFromJson(ArrayNode cashFlowStatements) {
    List<StockCashflowStatement> stockCashFlowStatementList = new ArrayList<>();
    for (JsonNode item : cashFlowStatements) {
      Map<String, Long> jsonWithRawValues = extractRowValueFromEntries(item);
      StockCashflowStatement translatedObject = objectMapper.convertValue(jsonWithRawValues,
              StockCashflowStatement.class);
      translatedObject.setFreeCashFlow(
              translatedObject.getTotalCashFromOperatingActivities() + translatedObject.getCapitalExpenditures());
      stockCashFlowStatementList.add(translatedObject);
    }
    return stockCashFlowStatementList;
  }

  private Map<String, Long> extractRowValueFromEntries(JsonNode item) {
    Map<String, Long> jsonWithRawValues = new HashMap<>();
    item.fields().forEachRemaining(entry -> {
      JsonNode value = entry.getValue();
      if (value.has("raw")) {
        if (entry.getKey().equals("endDate")) {
          jsonWithRawValues.put(entry.getKey(), value.get("raw").asLong() * 1000);
          return;
        }
        jsonWithRawValues.put(entry.getKey(), value.get("raw").asLong());
      }
    });
    return jsonWithRawValues;
  }

  @Override
  @SneakyThrows
  public List<StockIncomeStatement> getIncomeStatements(String symbol) {
    String annualRequestUrl = UriComponentsBuilder.fromHttpUrl(incomeStatementsApi)
            .buildAndExpand(symbol)
            .toUriString();
    String quarterlyRequestUrl = UriComponentsBuilder.fromHttpUrl(incomeStatementsQuarterlyApi)
            .buildAndExpand(symbol)
            .toUriString();

    ResponseEntity<String> annualResponse = restTemplate.getForEntity(annualRequestUrl, String.class);
    ResponseEntity<String> quarterlyResponse = restTemplate.getForEntity(quarterlyRequestUrl, String.class);
    if ((annualResponse.getStatusCode() == HttpStatus.OK) && quarterlyResponse.getStatusCode() == HttpStatus.OK) {
      JsonNode annualJson = objectMapper.readTree(annualResponse.getBody());
      JsonNode quarterlyJson = objectMapper.readTree(quarterlyResponse.getBody());
      ArrayNode incomeStatements = (ArrayNode) annualJson.get("quoteSummary")
              .get("result")
              .get(0)
              .get("incomeStatementHistory")
              .get("incomeStatementHistory");
      ArrayNode quarterlyIncomeStatements = (ArrayNode) quarterlyJson.get("quoteSummary")
              .get("result")
              .get(0)
              .get("incomeStatementHistoryQuarterly")
              .get("incomeStatementHistory");

      Optional<StockIncomeStatement> ttmIncomeStatements = parseStockIncomeStatementsFromJson(
              quarterlyIncomeStatements)
              .stream()
              .reduce(StockIncomeStatement::add);

      List<StockIncomeStatement> result = parseStockIncomeStatementsFromJson(incomeStatements);
      ttmIncomeStatements.ifPresent(stockIncomeStatement -> result.add(0, stockIncomeStatement));
      return result;
    }
    return new ArrayList<>();
  }

  @Override
  @SneakyThrows
  public List<StockBalanceSheet> getBalanceSheets(String symbol) {
    String annualRequestUrl = UriComponentsBuilder.fromHttpUrl(balanceSheetApi)
            .buildAndExpand(symbol)
            .toUriString();
    String quarterlyRequestUrl = UriComponentsBuilder.fromHttpUrl(balanceSheetQuarterlyApi)
            .buildAndExpand(symbol)
            .toUriString();
    ResponseEntity<String> annualResponse = restTemplate.getForEntity(annualRequestUrl, String.class);
    ResponseEntity<String> quarterlyResponse = restTemplate.getForEntity(quarterlyRequestUrl, String.class);
    if ((annualResponse.getStatusCode() == HttpStatus.OK) && quarterlyResponse.getStatusCode() == HttpStatus.OK) {
      JsonNode annualJson = objectMapper.readTree(annualResponse.getBody());
      JsonNode quarterlyJson = objectMapper.readTree(quarterlyResponse.getBody());
      ArrayNode balanceSheets = (ArrayNode) annualJson.get("quoteSummary")
              .get("result")
              .get(0)
              .get("balanceSheetHistory")
              .get("balanceSheetStatements");
      ArrayNode quarterlyBalanceSheets = (ArrayNode) quarterlyJson.get("quoteSummary")
              .get("result")
              .get(0)
              .get("balanceSheetHistoryQuarterly")
              .get("balanceSheetStatements");
      List<StockBalanceSheet> stockBalanceSheetsList = new ArrayList<>();

      StockBalanceSheet ttmBalanceSheet = null;

      for (JsonNode item : quarterlyBalanceSheets) {
        StockBalanceSheet convertedValue = objectMapper.convertValue(item, StockBalanceSheet.class);
        if (ttmBalanceSheet == null) {
          ttmBalanceSheet = convertedValue;
        } else {
          ttmBalanceSheet.add(convertedValue);
        }
      }

      for (JsonNode item : balanceSheets) {
        stockBalanceSheetsList.add(objectMapper.convertValue(item, StockBalanceSheet.class));
      }

      stockBalanceSheetsList.add(0, ttmBalanceSheet);

      return stockBalanceSheetsList;
    }
    return new ArrayList<>();
  }

  private List<StockIncomeStatement> parseStockIncomeStatementsFromJson(ArrayNode stockIncomeStatementJson) {
    List<StockIncomeStatement> stockIncomeStatementList = new ArrayList<>();
    for (JsonNode item : stockIncomeStatementJson) {
      Map<String, Long> jsonWithRawValues = extractRowValueFromEntries(item);
      StockIncomeStatement translatedObject = objectMapper.convertValue(jsonWithRawValues,
              StockIncomeStatement.class);
      stockIncomeStatementList.add(translatedObject);
    }
    return stockIncomeStatementList;
  }

}
