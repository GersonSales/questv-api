package com.questv.api.series;

import com.questv.api.question.Question;
import com.questv.api.question.Questionable;

import java.util.HashSet;
import java.util.Set;

public class Series implements Questionable {
  private final String name;
  private final Set<Season> seasons;

  private final Set<Question> questionSet;


  /*default*/ Series(final String name) {
    this.name = name;
    questionSet = new HashSet<>();
    seasons = new HashSet<>();
  }

  public void attachQuestion(final Question question) {
    this.questionSet.add(question);
  }

  public Set<Question> getQuestions() {
    return this.questionSet;
  }

  @Override
  public void attachAll(final Set<Question> questionSet) {
    this.questionSet.addAll(questionSet);
  }

  public String getName() {
    return name;
  }
}
