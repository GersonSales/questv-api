package com.questv.series;

import com.questv.question.Question;
import com.questv.question.Questionable;

import java.util.HashSet;
import java.util.Set;

public class Episode implements Questionable {
  private final Set<Question> questionSet;
  private final String name;

  /*default*/ Episode(String name) {
    this.name = name;
    questionSet = new HashSet<>();
  }


  @Override
  public void attachQuestion(final Question question) {
    this.questionSet.add(question);
  }

  @Override
  public void attachQuestions(Set<Question> questionSet) {

  }

  @Override
  public Set<Question> getQuestions() {
    return this.questionSet;
  }
}
