package com.adamnagyan.yahoofinancewebapi.repositories.stock;

import com.adamnagyan.yahoofinancewebapi.exceptions.RequestFailedException;
import com.adamnagyan.yahoofinancewebapi.model.stock.StockCashflowStatements;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${external-apis.yahoofinance-cashflow}")
  private String cashFlowStatementApi;

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
  public List<StockCashflowStatements> getCashFlowStatements(String symbol) throws JsonProcessingException {
    String requestUrl = UriComponentsBuilder.fromHttpUrl(cashFlowStatementApi)
            .path("/{symbol}")
            .queryParam("modules", "cashflowStatementHistory")
            .buildAndExpand(symbol).toUriString();
    ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
    if (response.getStatusCode() == HttpStatus.OK) {
      JsonNode json = objectMapper.readTree(response.getBody());
      List<StockCashflowStatements> stockCashFlowStatementList = new ArrayList<>();
      ArrayNode cashFlowStatements = (ArrayNode) json.get("quoteSummary").get("result").get(0).get("cashflowStatementHistory").get("cashflowStatements");

      for (JsonNode item : cashFlowStatements) {
        Map<String, Long> jsonWithRawValues = extractRowValueFromEntries(item);
        StockCashflowStatements translatedObject = objectMapper.convertValue(jsonWithRawValues, StockCashflowStatements.class);
        translatedObject.setFreeCashFlow(
                translatedObject.getTotalCashFromOperatingActivities() - translatedObject.getCapitalExpenditures()
        );
        stockCashFlowStatementList.add(translatedObject);
      }
      return stockCashFlowStatementList;
    }
    throw new RequestFailedException("cashflowStatementHistory request failed");
  }
}
