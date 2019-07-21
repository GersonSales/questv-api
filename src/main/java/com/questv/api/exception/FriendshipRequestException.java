package com.questv.api.exception;

public class FriendshipRequestException extends FriendshipException {
  public FriendshipRequestException() {
    super("Friendship Request Exception");
  }

  public FriendshipRequestException(String message) {
    super(message);
  }
}
