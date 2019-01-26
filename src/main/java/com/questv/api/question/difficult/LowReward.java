package com.questv.api.question.difficult;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class LowReward implements Reward {

  @Override
  public Integer getPoints() {
    return 3;
  }
}
