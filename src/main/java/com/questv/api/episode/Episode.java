package com.questv.api.episode;

import com.questv.api.question.Question;
import com.questv.api.question.Questionable;

import java.util.HashSet;
import java.util.Set;

public class Episode implements Questionable {
  private final String name;
  private final Set<Question> questionSet;

  /*default*/ Episode(String name) {
    this.name = name;
    questionSet = new HashSet<>();
  }


  @Override
  public void attachQuestion(final Question question) {
    this.questionSet.add(question);
  }

  @Override
  public void attachAll(Set<Question> questionSet) {

  }

  @Override
  public Set<Question> getQuestions() {
    return this.questionSet;
  }
}
