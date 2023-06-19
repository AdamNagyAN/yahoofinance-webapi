package com.adamnagyan.yahoofinancewebapi.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.login.CredentialExpiredException;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
	private final BaseAppExceptionMapper baseAppExceptionMapper;

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestExceptionHandling(BadRequestException ex) {
		return new ResponseEntity<>(
				new ExceptionBody(ErrorCode.OO_INVALID_ARGUMENT_ERROR, new Date(), ex.getArgument(), ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> generalExceptionHandling() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_GENERAL_ERROR, new Date()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BaseAppException.class)
	public ResponseEntity<?> baseAppExceptionHandling(BaseAppException ex) {
		return new ResponseEntity<>(baseAppExceptionMapper.toExceptionDto(ex), ex.getHttpStatus());
	}



	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {

		List<ObjectError> fieldErrors = ex.getAllErrors();
		return new ResponseEntity<>(
				new ExceptionBody(ErrorCode.OO_INVALID_ARGUMENT_ERROR, new Date(),
						((FieldError) fieldErrors.get(0)).getField(), fieldErrors.get(0).getDefaultMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserAlreadyExistAuthenticationException.class)
	public ResponseEntity<?> userAlreadyExistsExceptionHandling() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_USER_ALREADY_EXISTS, new Date()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> expiredJwtExceptionHandling() {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(CredentialExpiredException.class)
	public ResponseEntity<?> invalidCredentialsExceptionHandling() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_INVALID_CREDENTIALS, new Date()),
				HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> badCredentialsExceptionHandling() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_INVALID_CREDENTIALS, new Date()),
				HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<?> disabledUserExceptionHandler() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_DISABLED_USER, new Date()), HttpStatus.FORBIDDEN);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(RecaptchaException.class)
	public ResponseEntity<?> recaptchaExceptionHandler() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_RECAPTCHA_ERROR, new Date()), HttpStatus.FORBIDDEN);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> userNotFoundException() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_USER_NOT_FOUND, new Date()), HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(UserIsAlreadyEnabledException.class)
	public ResponseEntity<?> userAlreadyEnabledException() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_USER_ALREADY_ENABLED, new Date()),
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ConfirmationTokenNotFoundException.class)
	public ResponseEntity<?> confirmationTokenNotFoundException() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_CONFIRMATION_TOKEN_NOT_FOUND, new Date()),
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ConfirmationTokenExpiredException.class)
	public ResponseEntity<?> confirmationTokenExpiredException() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_CONFIRMATION_TOKEN_EXPIRED, new Date()),
				HttpStatus.FORBIDDEN);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(MailSendException.class)
	public ResponseEntity<?> mailSendingException() {
		return new ResponseEntity<>(new ExceptionBody(ErrorCode.OO_MAIL_SENDING_ERROR, new Date()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
