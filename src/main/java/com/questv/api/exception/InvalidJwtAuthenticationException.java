package com.questv.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJwtAuthenticationException extends AuthenticationException {

  private static final long serialVersionUID = -6866857265357011071L;

  public InvalidJwtAuthenticationException(String message) {
    super(message);
  }
}
