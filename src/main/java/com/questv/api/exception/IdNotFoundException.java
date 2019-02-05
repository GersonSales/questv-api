package com.questv.api.exception;

public class IdNotFoundException extends RuntimeException {
  public IdNotFoundException() {
    super("Cannot find a model with given id.");
  }

  public IdNotFoundException(String message) {
    super(message);
  }
}
