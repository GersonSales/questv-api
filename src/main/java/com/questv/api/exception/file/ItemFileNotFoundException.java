package com.questv.api.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemFileNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -7483809638884860538L;

  public ItemFileNotFoundException() {
    super("File upload exception.");
  }

  public ItemFileNotFoundException(String message) {
    super(message);
  }

  public ItemFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
