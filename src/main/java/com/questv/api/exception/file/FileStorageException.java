package com.questv.api.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FileStorageException extends RuntimeException {
  private static final long serialVersionUID = -7483809638884860538L;

  public FileStorageException() {
    super("File upload exception.");
  }

  public FileStorageException(String message) {
    super(message);
  }

  public FileStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
