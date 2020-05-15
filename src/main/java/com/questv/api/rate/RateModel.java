package com.questv.api.rate;

import com.questv.api.contracts.Convertible;
import com.questv.api.user.UserModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class RateModel implements Convertible<RateDTO> {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


//  private UserModel ratable;

  private float value;
  private int votes;


  public Long getId() {
    return id;
  }

  public float getValue() {
    return value;
  }

  public void vote(final float value) {
    this.value = (this.value + value) / ++this.votes;
  }

  @Override
  public RateDTO convert() {
    return new RateDTO(value);
  }
}
