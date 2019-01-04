package com.questv.question.difficult;

public class DifficultPoints {
  private int evaluations;
  private float evaluationSum;

  public DifficultPoints(final float initialPoints) {
    this.evaluations++;
    this.evaluationSum = initialPoints;
  }

  public DifficultPoints() {
  }

  public void evaluate(final float evaluation) {
      this.evaluations++;
      this.evaluationSum = this.evaluationSum + evaluation;
  }

  public float getValue() {
    return this.evaluationSum / (this.evaluations == 0 ? 1 : this.evaluations);
  }


}
