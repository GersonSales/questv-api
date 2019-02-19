package com.questv.api.exception;

public class AnswerNotFoundException extends RuntimeException {
  public AnswerNotFoundException() {
    super("Answer not found.");
  }

  public AnswerNotFoundException(String message) {
    super(message);
  }
}
