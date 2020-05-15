package com.questv.api.exception.credential;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomizedBadCredentialsException extends BadCredentialsException {

  private static final long serialVersionUID = 5928462060606844L;

  public CustomizedBadCredentialsException(String msg) {
    super(msg);
  }

  public CustomizedBadCredentialsException(String msg, Throwable t) {
    super(msg, t);
  }

  public CustomizedBadCredentialsException(Throwable t) {
    super("Bad Credentials Exception", t);
  }
}
