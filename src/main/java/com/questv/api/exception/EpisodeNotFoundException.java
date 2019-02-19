package com.questv.api.exception;

public class EpisodeNotFoundException extends RuntimeException {
  public EpisodeNotFoundException() {
    super("Episode not found.");
  }

  public EpisodeNotFoundException(String message) {
    super(message);
  }
}
