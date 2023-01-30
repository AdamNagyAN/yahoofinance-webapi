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
  public Long raw;
  public String fmt;
}
