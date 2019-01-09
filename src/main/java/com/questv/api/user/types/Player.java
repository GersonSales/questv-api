package com.questv.api.user.types;

import com.questv.api.user.UserModel;
import com.questv.api.user.properties.Name;

public class Player extends UserModel {
  private float score;

  public Player(final Name name, final String email, final String password) {
    super(name, email, password);
  }
}
