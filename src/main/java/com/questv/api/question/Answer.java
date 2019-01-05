package com.questv.api.question;

public class Answer {
  private final String value;
  private final boolean isCorrect;


  /*default*/ Answer(final String value, final boolean isCorrect) {
    this.value = value;
    this.isCorrect = isCorrect;
  }

  public String getValue() {
    return value;
  }

  public boolean isCorrect() {
    return isCorrect;
  }
}
