package com.adamnagyan.yahoofinancewebapi.repositories.stock;

import com.adamnagyan.yahoofinancewebapi.exceptions.RequestFailedException;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatements;
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


  @Override
  @SneakyThrows
  public List<StockCashflowStatements> getCashFlowStatements(String symbol) {
    String annualRequestUrl = UriComponentsBuilder.fromHttpUrl(cashFlowStatementApi)
            .path("/{symbol}")
            .queryParam("modules", "cashflowStatementHistory")
            .buildAndExpand(symbol).toUriString();
    String quarterlyRequestUrl = UriComponentsBuilder.fromHttpUrl(cashFlowStatementApi)
            .path("/{symbol}")
            .queryParam("modules", "cashflowStatementHistoryQuarterly")
            .buildAndExpand(symbol).toUriString();
    ResponseEntity<String> annualResponse = restTemplate.getForEntity(annualRequestUrl, String.class);
    ResponseEntity<String> quarterlyResponse = restTemplate.getForEntity(quarterlyRequestUrl, String.class);
    if ((annualResponse.getStatusCode() == HttpStatus.OK) && quarterlyResponse.getStatusCode() == HttpStatus.OK) {
      JsonNode annualJson = objectMapper.readTree(annualResponse.getBody());
      JsonNode quarterlyJson = objectMapper.readTree(quarterlyResponse.getBody());
      ArrayNode cashFlowStatements = (ArrayNode) annualJson.get("quoteSummary").get("result").get(0).get("cashflowStatementHistory").get("cashflowStatements");
      ArrayNode quarterlyCashFlowStatements = (ArrayNode) quarterlyJson.get("quoteSummary").get("result").get(0).get("cashflowStatementHistoryQuarterly").get("cashflowStatements");
      Optional<StockCashflowStatements> ttmCashFlowStatements = parseCashflowStatementsFromJson(quarterlyCashFlowStatements).stream().reduce(StockCashflowStatements::add);
      List<StockCashflowStatements> result = parseCashflowStatementsFromJson(cashFlowStatements);
      ttmCashFlowStatements.ifPresent(stockCashflowStatements -> result.add(0, stockCashflowStatements));
      return result;
    }
    throw new RequestFailedException("cashflowStatementHistory request failed");
  }

  private List<StockCashflowStatements> parseCashflowStatementsFromJson(ArrayNode cashFlowStatements) {
    List<StockCashflowStatements> stockCashFlowStatementList = new ArrayList<>();
    for (JsonNode item : cashFlowStatements) {
      Map<String, Long> jsonWithRawValues = extractRowValueFromEntries(item);
      StockCashflowStatements translatedObject = objectMapper.convertValue(jsonWithRawValues, StockCashflowStatements.class);
      translatedObject.setFreeCashFlow(
              translatedObject.getTotalCashFromOperatingActivities() + translatedObject.getCapitalExpenditures()
      );
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
}
