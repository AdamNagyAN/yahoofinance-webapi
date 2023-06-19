package com.adamnagyan.yahoofinancewebapi.exceptions;

import org.springframework.http.HttpStatus;

public class BaseAppExceptionFactory {
  public static BaseAppException forbidden() {
    return new BaseAppException(ErrorCode.OO_PERMISSION_DENIED, "Permission denied", HttpStatus.FORBIDDEN);
  }
}
