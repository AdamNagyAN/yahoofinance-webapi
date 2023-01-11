package com.adamnagyan.yahoofinancewebapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> badRequestExceptionHandling(Exception exception, WebRequest request) {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_INVALID_ARGUMENT_ERROR,
                    new Date(),
                    exception.getMessage()),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> generalExceptionHandling(Exception exception, WebRequest request) {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_GENERAL_ERROR, new Date()),
            HttpStatus.BAD_REQUEST
    );
  }
}
