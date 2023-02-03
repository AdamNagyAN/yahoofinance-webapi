package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;

public interface ConfirmationTokenService {
  public void saveConfirmationToken(ConfirmationToken token);
}
