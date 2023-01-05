package com.adamnagyan.yahoofinancewebapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionBody {
  @NonNull
  private ErrorCode code;
  @NonNull
  private Date timestamp;
  private String parameter;

}
