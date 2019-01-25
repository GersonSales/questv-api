package com.questv.api.season;

import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
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
    this.seasonService.createAndAttach(seasonDTO.getSeriesId(), seasonDTO);
    return seasonDTO;//TODO
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

  @Override
  @PutMapping("/season")
  public void put(@Valid @RequestBody final SeasonDTO seasonDTO) {
    this.seasonService.updateById(seasonDTO.getId(), seasonDTO);

  }
}
