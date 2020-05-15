package com.questv.api.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountCreationException extends RuntimeException {

  private static final long serialVersionUID = -6866857265357011071L;

  public AccountCreationException() {
    this("User already exists.");
  }

  public AccountCreationException(final String message) {
    super(message);
  }

  public AccountCreationException(final Throwable t) {
    super("Error creating account", t);
  }
}
