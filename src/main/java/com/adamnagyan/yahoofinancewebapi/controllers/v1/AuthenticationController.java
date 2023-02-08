package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.ResendRequestDto;
import com.adamnagyan.yahoofinancewebapi.exceptions.UserAlreadyExistAuthenticationException;
import com.adamnagyan.yahoofinancewebapi.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@Valid @RequestBody RegisterRequestDto request) throws UserAlreadyExistAuthenticationException {
    service.register(request);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public AuthenticationResponseDto login(@Valid @RequestBody AuthenticationRequestDto request) {
    return service.authenticate(request);
  }

  @GetMapping("/confirm")
  @ResponseStatus(HttpStatus.OK)
  public void confirm(@RequestParam("token") String token) {
    service.confirmRegistrationToken(token);
  }

  @PostMapping("/resend-email")
  @ResponseStatus(HttpStatus.OK)
  public void resendEmail(@Valid @RequestBody ResendRequestDto request) {
    service.sendConfirmationEmail(request.getEmail());
  }
}
