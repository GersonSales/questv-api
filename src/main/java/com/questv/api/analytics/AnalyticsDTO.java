package com.questv.api.analytics;

import com.questv.api.analytics.model.AnsweredItem;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsDTO {
  private Integer totalOfAnsweredQuestions;
  private Integer correctAnsweredQuestions;
  private Integer wrongAnsweredQuestions;

  private List<AnsweredItem> answeredSeries;
  private List<AnsweredItem> answeredCategories;

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

  public List<AnsweredItem> getAnsweredSeries() {
    return answeredSeries;
  }

  public void setAnsweredSeries(List<AnsweredItem> answeredSeries) {
    this.answeredSeries = answeredSeries;
  }

  public List<AnsweredItem> getAnsweredCategories() {
    return answeredCategories;
  }

  public void setAnsweredCategories(List<AnsweredItem> answeredCategories) {
    this.answeredCategories = answeredCategories;
  }

  public void attachAnsweredSeries(final AnsweredItem answeredItem) {
    getAnsweredSeries().add(answeredItem);
  }
  }
