package com.questv.series;

import com.questv.question.Question;
import com.questv.question.Questionable;

import java.util.HashSet;
import java.util.Set;

public class Season implements Questionable {

  private final Set<Episode> episodeSet;
  private final Set<Question> questionSet;
  private final String name;

  /*default*/ Season(final String name) {
    this.name = name;
    episodeSet = new HashSet<>();
    questionSet = new HashSet<>();
  }

  @Override
  public void attachQuestion(Question question) {
    this.questionSet.add(question);
  }

  @Override
  public Set<Question> getQuestions() {
    return this.questionSet;
  }
}
