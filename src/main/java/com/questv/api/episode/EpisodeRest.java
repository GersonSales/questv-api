package com.questv.api.episode;

import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/series/{seriesId}/seasons/{seasonId}/episodes")
public class EpisodeRest  {

  private final EpisodeService episodeService;

  public EpisodeRest(final EpisodeService episodeService) {
    this.episodeService = episodeService;
    assert this.episodeService != null;
  }

  
  @PostMapping()
  public ResponseEntity<EpisodeDTO> post(@PathVariable("seriesId") final Long seriesId,
                                         @PathVariable("seasonId") final Long seasonId,
                                         @Valid @RequestBody EpisodeDTO episodeDTO) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.episodeService.createAndAttach(seasonId, episodeDTO));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping()
  public ResponseEntity<List<EpisodeDTO>> getAllBySeason(@PathVariable("seriesId") final Long seriesId,
                                                         @PathVariable final Long seasonId) {
    try {
      return ResponseEntity.ok().body(this.episodeService.findAllByParent(seasonId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @GetMapping("/episodesasd")
  public ResponseEntity<List<EpisodeDTO>> get() {
    return ResponseEntity.ok().body(this.episodeService.findAll());
  }

  
  @GetMapping("/{episodeId}")
  public ResponseEntity<EpisodeDTO> get(@PathVariable("seriesId") final Long seriesId,
                                        @PathVariable final Long seasonId,
                                        @PathVariable final Long episodeId) {
    try {
      return ResponseEntity.ok().body(this.episodeService.findById(episodeId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @DeleteMapping("/{episodeId}")
  public void delete(@PathVariable("seriesId") final Long seriesId,
                     @PathVariable final Long seasonId,
                     @PathVariable Long episodeId) {
    this.episodeService.delete(seasonId, episodeId);
  }

  
  @PutMapping("/{episodeId}")
  public void put(@PathVariable("seriesId") final Long seriesId,
                  @PathVariable final Long seasonId,
                  @PathVariable("episodeId") final Long episodeId,
                  final EpisodeDTO episodeDTO) {
    episodeDTO.setId(episodeId);
    this.episodeService.update(episodeDTO);
  }
}
