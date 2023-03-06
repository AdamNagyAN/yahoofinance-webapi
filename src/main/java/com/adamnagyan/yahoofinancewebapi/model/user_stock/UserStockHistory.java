package com.adamnagyan.yahoofinancewebapi.model.user_stock;

import com.adamnagyan.yahoofinancewebapi.model.user.User;

import javax.persistence.*;

@Entity
public class UserStockHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  private String symbol;
  private Double quantity;
  private Double transactionFee;
  private Double price;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User user;
}
