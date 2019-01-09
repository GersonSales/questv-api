package com.questv.api.user.types;

import com.questv.api.user.UserModel;
import com.questv.api.user.properties.Name;

public class Player extends UserModel {
  private float score;

  public Player(Name name) {
    super(name);
  }
}
