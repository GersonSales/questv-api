package com.questv.api.file.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QtvFileNotFoundException extends RuntimeException {
  public QtvFileNotFoundException(String message) {
    super(message);
  }

  public QtvFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}