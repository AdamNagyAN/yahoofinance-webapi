package com.adamnagyan.yahoofinancewebapi.auth.service;

import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.auth.dto.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.UserAlreadyExistAuthenticationException;

public interface AuthenticationService {

	void register(RegisterRequestDto request) throws UserAlreadyExistAuthenticationException;

	AuthenticationResponseDto login(AuthenticationRequestDto request);

}
