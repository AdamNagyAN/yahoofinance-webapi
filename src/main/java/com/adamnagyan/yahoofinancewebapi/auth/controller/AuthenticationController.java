package com.adamnagyan.yahoofinancewebapi.auth.controller;

import com.adamnagyan.yahoofinancewebapi.auth.dto.*;
import com.adamnagyan.yahoofinancewebapi.auth.service.AuthenticationService;
import com.adamnagyan.yahoofinancewebapi.auth.service.ConfirmationTokenService;
import com.adamnagyan.yahoofinancewebapi.auth.service.NewPasswordService;
import com.adamnagyan.yahoofinancewebapi.common.exceptions.UserAlreadyExistAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService service;

	private final ConfirmationTokenService confirmationTokenService;

	private final NewPasswordService newPasswordService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody RegisterRequestDto request)
			throws UserAlreadyExistAuthenticationException {
		service.register(request);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public AuthenticationResponseDto login(@Valid @RequestBody AuthenticationRequestDto request) {
		return service.login(request);
	}

	@GetMapping("/confirm")
	@ResponseStatus(HttpStatus.OK)
	public void confirm(@RequestParam("token") String token) {
		confirmationTokenService.confirmRegistrationToken(token);
	}

	@PostMapping("/resend-email")
	@ResponseStatus(HttpStatus.OK)
	public void resendEmail(@Valid @RequestBody ResendRequestDto request) {
		confirmationTokenService.sendConfirmationEmail(request.getEmail());
	}

	@PostMapping("/new-password")
	@ResponseStatus(HttpStatus.CREATED)
	public void newPassword(@Valid @RequestBody EmailRequestDto requestDto) {
		newPasswordService.generateNewPasswordToken(requestDto.getEmail());
	}

	@PutMapping("/change-password")
	@ResponseStatus(HttpStatus.OK)
	public void changePassword(@Valid @RequestBody ResetPasswordDto request) {
		newPasswordService.changePassword(request.getToken(), request.getPassword());
	}

}
