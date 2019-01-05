package com.questv.api.question.difficult;

public class HardDifficult implements Difficult {
  public float getPoints() {
    return 3;
  }

  @Override
  public String toString() {
    return "Hard Difficult";
  }
}
