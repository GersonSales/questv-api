package com.questv.api.question;

import java.util.Set;

public final class Question {

  private final String description;
  private final Set<Answer> answerSet;
  private final Evaluation evaluation;


  /*default*/ float getReward() {
    return this.evaluation.getReward();
  }

  /*default*/ Question(final String description, final Set<Answer> answerSet) {
    this.description = description;
    this.answerSet = answerSet;
    evaluation = new Evaluation();
  }


  public String getDescription() {
    return description;
  }

  void evaluate(final float difficultPoints) {
    this.evaluation.evaluate(difficultPoints);
  }

  @Override
  public String toString() {
    return "Question"
        .concat("\nDescription: ")
        .concat(this.description)
        .concat("\nReward: ")
        .concat(getReward() + "")
        .concat("\nEvaluation: ")
        .concat(getDifficultPoints() + "")
        .concat("\nDifficult: ")
        .concat(this.evaluation.getDifficult().toString());
  }

  private float getDifficultPoints() {
    return this.evaluation.getEvaluationAverage();
  }
}
