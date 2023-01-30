package com.adamnagyan.yahoofinancewebapi.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementValue {
  public Long raw = 0L;
  public String fmt;

  public StatementValue add(StatementValue that) {
    return StatementValue.builder()
            .raw(this.raw + that.raw)
            .build();
  }
}
