package com.adamnagyan.yahoofinancewebapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
public class CustomExceptionHandler {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> badRequestExceptionHandling() {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_INVALID_ARGUMENT_ERROR,
                    new Date()),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> generalExceptionHandling() {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_GENERAL_ERROR, new Date()),
            HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> badCredentialsExceptionHandling() {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_USER_NOT_FOUND, new Date()),
            HttpStatus.NOT_FOUND
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(
          MethodArgumentNotValidException ex) {

    List<ObjectError> fieldErrors = ex.getAllErrors();
    return new ResponseEntity<>(
            new ExceptionBody(
                    ErrorCode.OO_INVALID_ARGUMENT_ERROR, new Date(),
                    ((FieldError) fieldErrors.get(0)).getField(),
                    fieldErrors.get(0).getDefaultMessage()),
            HttpStatus.NOT_FOUND
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
  public ResponseEntity<?> userAlreadyExistsExceptionHandling() {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_USER_ALREADY_EXISTS, new Date()),
            HttpStatus.BAD_REQUEST
    );
  }


  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(CredentialExpiredException.class)
  public ResponseEntity<?> invalidCredentialsExceptionHandling() {
    return new ResponseEntity<>(
            new ExceptionBody(ErrorCode.OO_INVALID_CREDENTIALS, new Date()),
            HttpStatus.UNAUTHORIZED
    );
  }


}
