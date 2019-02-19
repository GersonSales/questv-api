package com.questv.api.episode;

import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/series/{seriesId}/seasons/{seasonNumber}/episodes")
public class EpisodeRest  {

  private final EpisodeService episodeService;

  public EpisodeRest(final EpisodeService episodeService) {
    this.episodeService = episodeService;
    assert this.episodeService != null;
  }

  
  @PostMapping()
  public ResponseEntity<EpisodeDTO> post(@PathVariable final Long seriesId,
                                         @PathVariable final Integer seasonNumber,
                                         @Valid @RequestBody EpisodeDTO episodeDTO) {
    try {
      final EpisodeDTO attachedEpisode = this.episodeService.createAndAttach(seriesId, seasonNumber, episodeDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(attachedEpisode);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping()
  public ResponseEntity<List<EpisodeDTO>> getAllBySeason(@PathVariable final Long seriesId,
                                                         @PathVariable final Integer seasonNumber) {
    try {
      return ResponseEntity.ok().body(this.episodeService.findAllByParent(seriesId, seasonNumber));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @GetMapping("/episodesasd")
  public ResponseEntity<List<EpisodeDTO>> get() {
    return ResponseEntity.ok().body(this.episodeService.findAll());
  }

  
  @GetMapping("/{episodeNumber}")
  public ResponseEntity<EpisodeDTO> get(@PathVariable final Long seriesId,
                                        @PathVariable final Integer seasonNumber,
                                        @PathVariable final Integer episodeNumber) {
    try {
      return ResponseEntity.ok().body(this.episodeService.findByNumber(seriesId, seasonNumber, episodeNumber));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @DeleteMapping("/{episodeNumber}")
  public void delete(@PathVariable final Long seriesId,
                     @PathVariable final Integer seasonNumber,
                     @PathVariable final Integer episodeNumber) {
    this.episodeService.delete(seriesId, seasonNumber, episodeNumber);
  }

  
  @PutMapping("/{episodeNumber}")
  public void put(@PathVariable final Long seriesId,
                  @PathVariable final Integer seasonNumber,
                  @PathVariable final Integer episodeNumber,
                  final EpisodeDTO episodeDTO) {
    this.episodeService.update(seriesId, seasonNumber, episodeNumber, episodeDTO);
  }
}
