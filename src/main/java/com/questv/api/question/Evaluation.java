package com.questv.api.question;

import com.questv.api.question.difficult.Difficult;
import com.questv.api.question.difficult.EasyDifficult;
import com.questv.api.question.difficult.HardDifficult;
import com.questv.api.question.difficult.MediumDifficult;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {
  private final List<Float> history;
  private float evaluationSum;
  private int evaluationsCount;
  private Difficult difficult;

  public Evaluation() {
    history = new ArrayList<>();
  }

  public void evaluate(final float evaluation) {
    this.history.add(evaluation);
    this.evaluationSum = this.evaluationSum + evaluation;
    this.evaluationsCount++;
    refreshDifficult();
  }

  private void refreshDifficult() {
    if (getEvaluationAverage() < 5) {
      setDifficult(new EasyDifficult());
    } else if (getEvaluationAverage() < 8) {
      setDifficult(new MediumDifficult());
    } else {
      setDifficult(new HardDifficult());
    }
  }

  private void setDifficult(final Difficult difficult) {
    this.difficult = difficult;
  }

  public float getEvaluationAverage() {
    return this.evaluationSum / evaluationsCount == 0 ?  1 : this.evaluationsCount;
  }


  public Difficult getDifficult() {
    return this.difficult;
  }


  public float getReward() {
    return this.difficult.getPoints();
  }

}
