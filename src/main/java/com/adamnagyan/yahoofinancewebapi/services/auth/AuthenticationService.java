package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;

public interface AuthenticationService {
  void register(RegisterRequestDto request) throws UserAlreadyExistAuthenticationException;

  void sendConfirmationEmail(String email);

  AuthenticationResponseDto login(AuthenticationRequestDto request);


  void confirmRegistrationToken(String token);
}
