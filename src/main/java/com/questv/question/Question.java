package com.questv.question;

import com.questv.question.difficult.*;

public final class Question {
  private static int serializer;

  private final String description;
  private Difficult difficult;
  private DifficultPoints difficultPoints;
  private final int id;


  public Question(final String description, final float difficultPoints) {
    this(description);
    this.difficultPoints = new DifficultPoints(difficultPoints);
    refreshDifficult();
  }

  public float getReward() {
    return this.difficult.getPoints();
  }

  private void refreshDifficult() {
    if (getDifficultPoints() < 5) {
      setDifficult(new EasyDifficult());
    } else if (getDifficultPoints() < 8) {
      setDifficult(new MediumDifficult());
    } else {
      setDifficult(new HardDifficult());
    }
  }

  public Question(final String description) {
    this.description = description;
    this.difficultPoints = new DifficultPoints();
    this.difficult = new EasyDifficult();
    id = serializer++;
  }

  private void setDifficult(final Difficult difficult) {
    this.difficult = difficult;
  }

  public String getDescription() {
    return description;
  }

  private float getDifficultPoints() {
    return difficultPoints.getValue();
  }

  void evaluate(final float difficultPoints) {
    this.difficultPoints.evaluate(difficultPoints);
    refreshDifficult();
  }

  @Override
  public String toString() {
    return "Question("
        .concat(id + "")
        .concat(")")
        .concat("\nDescription: ")
        .concat(this.description)
        .concat("\nReward: ")
        .concat(getReward() + "")
        .concat("\nEvaluation: ")
        .concat(getDifficultPoints() + "")
        .concat("\nDifficult: ")
        .concat(this.difficult.toString());
  }
}
