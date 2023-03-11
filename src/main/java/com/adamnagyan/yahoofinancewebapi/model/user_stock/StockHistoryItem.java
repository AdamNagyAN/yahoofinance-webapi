package com.adamnagyan.yahoofinancewebapi.model.user_stock;

import com.adamnagyan.yahoofinancewebapi.model.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  private String symbol;
  @NotNull
  private Double quantity;
  private Double transactionFee = 0.0;
  @NotNull
  private Double price;
  private LocalDate buyDate;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User user;
}
