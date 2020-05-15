package com.questv.api.exception.user;

public class UserEmailNotFoundException extends UserNotFoundException {

  private static final long serialVersionUID = 3279104502450367079L;

  public UserEmailNotFoundException() {
    super("User email not found!");
  }

  public UserEmailNotFoundException(String message) {
    super(message);
  }
}
