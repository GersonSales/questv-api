package com.questv.api.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends AuthenticationException {

  private static final long serialVersionUID = -6866857265357011071L;

  public UserAlreadyExistsException() {
    this("User already exists.");
  }

  public UserAlreadyExistsException(String message) {
    super(message);
  }


}
