package com.adamnagyan.yahoofinancewebapi.stock.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class StockRepositoryImplTest {

  @Autowired
  private StockRepositoryImpl stockRepositoryImpl;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void getStockBalanceSheets() {
    stockRepositoryImpl.getBalanceSheets("asdasdas");
  }
}