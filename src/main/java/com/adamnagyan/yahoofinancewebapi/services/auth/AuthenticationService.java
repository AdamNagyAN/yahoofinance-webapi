package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;

public interface AuthenticationService {
  AuthenticationResponseDto register(RegisterRequestDto request) throws UserAlreadyExistAuthenticationException;

  AuthenticationResponseDto authenticate(AuthenticationRequestDto request);

  void confirmRegistrationToken(String token);
}
