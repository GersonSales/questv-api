package com.questv.question;

import com.questv.question.difficult.*;

public class Question {
  private final String description;
  private Difficult difficult;
  private DifficultPoints difficultPoints;

  public Question(final String description, final float difficultPoints) {
    this(description);
    this.difficultPoints = new DifficultPoints(difficultPoints);
    refreshDifficult();

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
  }

  private void setDifficult(final Difficult difficult) {
    this.difficult = difficult;
  }

  public String getDescription() {
    return description;
  }

  public float getDifficultPoints() {
    return difficultPoints.getPoints();
  }

  public void evaluate(final float difficultPoints) {

  }
}
