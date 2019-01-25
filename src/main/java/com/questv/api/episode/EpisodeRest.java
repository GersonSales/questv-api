package com.questv.api.episode;

import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class EpisodeRest implements Restable<EpisodeDTO> {

  private final EpisodeService episodeService;

  public EpisodeRest(final EpisodeService episodeService) {
    this.episodeService = episodeService;
    assert this.episodeService != null;
  }

  @Override
  @PostMapping("/episode")
  @ResponseStatus(HttpStatus.CREATED)
  public EpisodeDTO post(@Valid @RequestBody EpisodeDTO episodeDTO) {
    return this.episodeService.create(episodeDTO);
  }

  @Override
  @GetMapping("/episode")
  public List<EpisodeDTO> getAll() {
    return this.episodeService.findAll();
  }

  @Override
  @GetMapping("/episode/{episodeId}")
  public EpisodeDTO getById(@PathVariable Long episodeId) {
    return this.episodeService.findById(episodeId);
  }

  @Override
  @DeleteMapping("/episode/{episodeId}")
  public void deleteById(@PathVariable Long episodeId) {
    this.episodeService.deleteById(episodeId);

  }

  @Override
  @PutMapping("/episode")
  public void put(final EpisodeDTO episodeDTO) {
    this.episodeService.updateById(episodeDTO.getId(), episodeDTO);
  }
}
