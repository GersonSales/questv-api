package com.questv.api.analytics.model;

public class AnsweredSeries {
  private final Long id;
  private final Integer totalOfQuestions;
  private Integer answeredQuestions;
  private Integer correctAnsweredQuestions;
  private Integer wrongAnsweredQuestions;


  public AnsweredSeries(final Long id, final Integer totalOfQuestions) {
    this.id = id;
    this.totalOfQuestions = totalOfQuestions;
    this.answeredQuestions
        = this.correctAnsweredQuestions
        = this.wrongAnsweredQuestions = 0;
  }

  public Long getId() {
    return this.id;
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
