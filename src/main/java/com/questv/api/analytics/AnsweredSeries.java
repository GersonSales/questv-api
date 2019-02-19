package com.questv.api.analytics;

public class AnsweredSeries {
  private final String name;
  private final Integer totalOfQuestions;
  private Integer answeredQuestions;
  private Integer correctAnsweredQuestions;
  private Integer wrongAnsweredQuestions;


  public AnsweredSeries(final String name, final Integer totalOfQuestions) {
    this.name = name;
    this.totalOfQuestions = totalOfQuestions;
    this.answeredQuestions
        = this.correctAnsweredQuestions
        = this.wrongAnsweredQuestions = 0;
  }

  public String getName() {
    return name;
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
