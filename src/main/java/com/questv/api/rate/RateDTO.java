package com.questv.api.rate;

import com.questv.api.contracts.Convertible;

public class RateDTO implements Convertible<RateModel> {
  private float rate;

  public RateDTO() {
  }

  public RateDTO(float rate) {
    this.rate = rate;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(final float rate) {
    this.rate = rate;
  }

  @Override
  public RateModel convert() {
    return null; //new RateModel(ratable);
  }
}
