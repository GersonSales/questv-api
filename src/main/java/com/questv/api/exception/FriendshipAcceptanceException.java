package com.questv.api.exception;

public class FriendshipAcceptanceException extends FriendshipException {
  public FriendshipAcceptanceException() {
    super("Friendship Acceptance Exception");
  }

  public FriendshipAcceptanceException(String message) {
    super(message);
  }
}
