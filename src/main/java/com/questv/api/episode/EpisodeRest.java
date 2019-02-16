package com.questv.api.episode;

import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  @PostMapping("/episodes")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<EpisodeDTO> post(@Valid @RequestBody EpisodeDTO episodeDTO) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.episodeService.createAndAttach(episodeDTO));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/seasons/{seasonId}/episodes")
  public ResponseEntity<List<EpisodeDTO>> getAllBySeason(@PathVariable final String seasonId) {
    try {
      return ResponseEntity.ok().body(this.episodeService.findAllByParent(seasonId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  @GetMapping("/episodes")
  public ResponseEntity<List<EpisodeDTO>> get() {
    return ResponseEntity.ok().body(this.episodeService.findAll());
  }

  @Override
  @GetMapping("/episodes/{episodeId}")
  public ResponseEntity<EpisodeDTO> get(@PathVariable final String episodeId) {
    try {
      return ResponseEntity.ok().body(this.episodeService.findById(episodeId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  @DeleteMapping("/episodes/{episodeId}")
  public void delete(@PathVariable String episodeId) {
    this.episodeService.delete(episodeId);
  }

  @Override
  @PutMapping("/episodes/{episodeId}")
  public void put(@PathVariable("episodeId") final String episodeId, final EpisodeDTO episodeDTO) {
    episodeDTO.setId(episodeId);
    this.episodeService.update(episodeDTO);
  }
}
