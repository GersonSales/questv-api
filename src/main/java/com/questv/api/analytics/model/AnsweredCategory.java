package com.questv.api.analytics.model;

public class AnsweredCategory {
  private final String name;
  private final Integer correctAnswered;
  private final Integer wrongAnswered;

  public AnsweredCategory(final String name,
                          final Integer correctAnswered,
                          final Integer wrongAnswered) {
    this.name = name;
    this.correctAnswered = correctAnswered;
    this.wrongAnswered = wrongAnswered;
  }

  public String getName() {
    return name;
  }

  public Integer getCorrectAnswered() {
    return correctAnswered;
  }

  public Integer getWrongAnswered() {
    return wrongAnswered;
  }
}
