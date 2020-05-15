package com.questv.api.rate;

import org.springframework.stereotype.Service;

@Service
public final class RateService {

  private final RateRepository rateRepository;

  public RateService(final RateRepository rateRepository) {
    this.rateRepository = rateRepository;
  }


  public float rate(final String ratableId, final float rate) {
    return 1f; //this.rateRepository.findById(ratableId)
  }
}
