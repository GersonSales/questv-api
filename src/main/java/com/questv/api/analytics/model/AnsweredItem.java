package com.questv.api.analytics.model;

public class AnsweredItem {
  private final String name;
  private final Integer totalOfQuestions;
  private Integer answeredQuestions;
  private Integer correctAnsweredQuestions;
  private Integer wrongAnsweredQuestions;


  public AnsweredItem(final String name) {
    this.name = name;
    this.totalOfQuestions =
    this.answeredQuestions
        = this.correctAnsweredQuestions
        = this.wrongAnsweredQuestions = 0;
  }

  public String getId() {
    return this.name;
  }

  public Integer getAnsweredQuestions() {
    return answeredQuestions;
  }

  public Integer getCorrectAnsweredQuestions() {
    return correctAnsweredQuestions;
  }

  public Integer getWrongAnsweredQuestions() {
    return wrongAnsweredQuestions;
  }

  public Integer getTotalOfQuestions() {
    return totalOfQuestions;
  }

  public void IncrementCorrectAnswersCount() {
    this.answeredQuestions++;
    this.correctAnsweredQuestions++;
  }

  public void IncrementWrongAnswersCount() {
    this.answeredQuestions++;
    this.wrongAnsweredQuestions++;
  }
}
