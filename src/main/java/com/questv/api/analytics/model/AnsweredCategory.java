package com.questv.api.analytics.model;

public class AnsweredCategory {
  private final String name;
  private Integer answered;
  private Integer correctAnswered;
  private Integer wrongAnswered;

  public AnsweredCategory(final String name) {
    this.name = name;
    this.answered = 0;
    this.correctAnswered = 0;
    this.wrongAnswered = 0;
  }

  public String getName() {
    return name;
  }

  public Integer getAnswered() {
    return answered;
  }

  public Integer getCorrectAnswered() {
    return correctAnswered;
  }

  public Integer getWrongAnswered() {
    return wrongAnswered;
  }

  public void incrementCorrectAnsweredByOne() {
    this.correctAnswered++;
    this.answered++;
  }

  public void incrementWrongAnsweredByOne() {
    this.wrongAnswered++;
    this.answered++;
  }


}
