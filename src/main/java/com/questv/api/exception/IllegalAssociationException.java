package com.questv.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalAssociationException extends RuntimeException {
  public IllegalAssociationException() {
    super("Illegal association.");
  }

  public IllegalAssociationException(final String message) {
    super(message);
  }
}
