package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;

public interface AuthenticationService {
  public AuthenticationResponseDto register(RegisterRequestDto request);

  public AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
}
