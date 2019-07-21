package com.questv.api.exception;

public class FriendshipException extends RuntimeException{

  public FriendshipException() {
    super("Friendship Exception");
  }

  public FriendshipException(String message) {
    super(message);
  }
}
