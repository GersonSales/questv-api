package com.questv.api.season;

import com.questv.api.util.ObjectService;
import com.questv.api.util.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public class SeasonRest implements Restable<SeasonDTO> {
  private final ObjectService<SeasonDTO> seasonService;

  public SeasonRest(final ObjectService<SeasonDTO> seasonService) {
    this.seasonService = seasonService;
    assert this.seasonService != null;
  }


  @Override
  @PostMapping("/season")
  @ResponseStatus(value = HttpStatus.CREATED)
  @ResponseBody
  public SeasonDTO post(@Valid @RequestBody final SeasonDTO seasonDTO) {
    return this.seasonService.create(seasonDTO);
  }

  @Override
  @GetMapping("/season")
  public List<SeasonDTO> getAll() {
    return this.seasonService.findAll();
  }

  @Override
  @GetMapping("/season/{seasonId}")
  public SeasonDTO getById(@PathVariable final Long seasonId) {
    return this.seasonService.findById(seasonId);
  }

  @Override
  @DeleteMapping("/season/{seasonId}")
  public void deleteById(@PathVariable final Long seasonId) {
    this.seasonService.deleteById(seasonId);
  }
}
