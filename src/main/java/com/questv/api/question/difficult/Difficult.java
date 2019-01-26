package com.questv.api.question.difficult;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "difficult_table", schema = "questv_schema")
public final class Difficult {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private final int[] evaluations;

  @Embedded
  private Reward reward;

  /*default*/ Difficult() {
    this.evaluations = new int[6];
    this.reward = new LowReward();
  }

  public Difficult(final int rating) {
    this();
    this.evaluations[rating]++;
    updateRanking();
  }


  public void evaluate(final int rating) {
    if (rating < 0 || rating > 6) return;
    this.evaluations[rating]++;
    updateRanking();
  }

  private void updateRanking() {
    final int difficult = getDifficult();
    if (difficult < 3) {
      this.reward = new LowReward();
    } else if (difficult < 5) {
      this.reward = new MediumReward();
    } else {
      this.reward = new HighReward();
    }

  }

  public Integer getDifficult() {
    int max = evaluations[0];
    int result = 0;
    for (int i = 0; i < evaluations.length; i++) {
      if (evaluations[i] > max) {
        max = evaluations[i];
        result = i;
      }
    }
    return result;
  }

  public Integer getReward() {
    return reward.getPoints();
  }

  @Override
  public String toString() {
    return "Difficult{".concat(String.valueOf(getDifficult())).concat("}");
  }

  public int[] getEvaluations() {
    return evaluations;
  }

  public void setReward(Reward reward) {
    this.reward = reward;
  }

}
