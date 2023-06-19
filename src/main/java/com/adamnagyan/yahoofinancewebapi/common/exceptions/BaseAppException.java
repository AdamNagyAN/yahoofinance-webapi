package com.adamnagyan.yahoofinancewebapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BaseAppException extends RuntimeException {

  private ErrorCode code;
  private HttpStatus httpStatus;
  private transient Map<String, Object> attributes = new HashMap<>();

  protected BaseAppException() {
  }

  protected BaseAppException(ErrorCode code, String message) {
    this(code, message, (HttpStatus) null);
  }

  protected BaseAppException(ErrorCode code, String message, HttpStatus httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
  }

  protected BaseAppException(ErrorCode code, Throwable cause) {
    super(cause);
    this.code = code;
  }

  protected BaseAppException(ErrorCode code, String message, Map<String, Object> attributes) {
    this(code, message, null, attributes);
  }

  protected BaseAppException(ErrorCode code, String message, HttpStatus httpStatus, Map<String, Object> attributes) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
    this.attributes = attributes;
  }

  protected BaseAppException(ErrorCode code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  protected BaseAppException(ErrorCode code, String message, HttpStatus httpStatus, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.httpStatus = httpStatus;
  }
}
