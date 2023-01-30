package com.adamnagyan.yahoofinancewebapi.exceptions;


public class RequestFailedException extends Exception {
  public RequestFailedException(String msg) {
    super(msg);
  }
}
