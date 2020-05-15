package com.questv.api.exception;

import com.questv.api.exception.user.UserNotFoundException;

public class IdNotFoundException extends UserNotFoundException {
  private static final long serialVersionUID = 4958532319472541055L;

  public IdNotFoundException() {
    super("Cannot find a model with given id.");
  }

  public IdNotFoundException(String message) {
    super(message);
  }
}
