package com.adamnagyan.yahoofinancewebapi.exceptions;

public class BadRequestException extends Exception {
  public BadRequestException(String message) {
    super(message);
  }
}
