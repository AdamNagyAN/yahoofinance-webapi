package com.adamnagyan.yahoofinancewebapi.common.dto;

import java.util.HashMap;
import java.util.Map;

public class ExceptionDto {
  private String code;
  private Integer httpStatus;
  private Map<String, Object> attributes = new HashMap<>();
}
