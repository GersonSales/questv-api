package com.questv.api.exception;

public class SeasonNotFoundException extends RuntimeException {

  public SeasonNotFoundException() {
    super("Season not found.");
  }

  public SeasonNotFoundException(String message) {
    super(message);
  }
}
