package com.questv.api.analytics;

import com.questv.api.analytics.model.AnsweredSeries;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsDTO {
  private Integer totalOfAnsweredQuestions;
  private Integer correctAnsweredQuestions;
  private Integer wrongAnsweredQuestions;

  private List<AnsweredSeries> answeredSeries;

  public AnalyticsDTO() {
    this.answeredSeries = new ArrayList<>();
  }

  public Integer getTotalOfAnsweredQuestions() {
    return totalOfAnsweredQuestions;
  }

  public void setTotalOfAnsweredQuestions(Integer totalOfAnsweredQuestions) {
    this.totalOfAnsweredQuestions = totalOfAnsweredQuestions;
  }

  public Integer getCorrectAnsweredQuestions() {
    return correctAnsweredQuestions;
  }

  public void setCorrectAnsweredQuestions(Integer correctAnsweredQuestions) {
    this.correctAnsweredQuestions = correctAnsweredQuestions;
  }

  public Integer getWrongAnsweredQuestions() {
    return wrongAnsweredQuestions;
  }

  public void setWrongAnsweredQuestions(Integer wrongAnsweredQuestions) {
    this.wrongAnsweredQuestions = wrongAnsweredQuestions;
  }

  public List<AnsweredSeries> getAnsweredSeries() {
    return answeredSeries;
  }

  public void setAnsweredSeries(List<AnsweredSeries> answeredSeries) {
    this.answeredSeries = answeredSeries;
  }

  public void attachAnsweredSeries(final AnsweredSeries answeredSeries) {
    getAnsweredSeries().add(answeredSeries);
  }
  }
