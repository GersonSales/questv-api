package com.questv.api.question;

public enum QuestionType {
  SERIES, SEASON, EPISODE;

  public static QuestionType getValueOf(final String string) {
      return valueOf(string.toUpperCase());
  }
}
