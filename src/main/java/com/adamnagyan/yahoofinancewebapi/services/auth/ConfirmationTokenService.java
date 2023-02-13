package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.model.user.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
  public void saveConfirmationToken(ConfirmationToken token);

  public Optional<ConfirmationToken> getToken(String token);

  public Optional<ConfirmationToken> getToken(Long userId);

}
