package com.questv.api.rate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.questv.api.uitl.Strings.API_ENDPOINT;

@RestController
@RequestMapping(API_ENDPOINT + "/rate")
public class RateController {


  private final RateService rateService;

  public RateController(final RateService rateService) {
    this.rateService = rateService;
  }

  @PostMapping("{ratableId}")
  public ResponseEntity<Float>
  rate(@PathVariable final String ratableId,
       @RequestBody final float rate) {

    return null;//new ResponseEntity.ok(this.rateService.rate(ratableId, rate));


  }

}
