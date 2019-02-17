package com.questv.api.season;

import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/series/{seriesId}/seasons")
public class SeasonRest {
  private final SeasonService seasonService;

  public SeasonRest(final SeasonService seasonService) {
    this.seasonService = seasonService;
    assert this.seasonService != null;
  }

  @PostMapping()
  public ResponseEntity<SeasonDTO> post(@PathVariable("seriesId") final Long seriesId,
                                        @Valid @RequestBody final SeasonDTO seasonDTO) {
    try {
      SeasonDTO attachedSeason = this.seasonService.createAndAttach(seriesId, seasonDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(attachedSeason);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping()
  public ResponseEntity<List<SeasonDTO>> getAllBySeries(@PathVariable final Long seriesId) {
    try {
      final List<SeasonDTO> allByParent = this.seasonService.findAllByParent(seriesId);
      return ResponseEntity.ok(allByParent);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }
  
  @GetMapping("/{seasonId}")
  public ResponseEntity<SeasonDTO> get(@PathVariable final Long seriesId,
                                       @PathVariable final Long seasonId) {
    try {
      return ResponseEntity.ok(this.seasonService.findById(seasonId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  
  @DeleteMapping("/{seasonId}")
  public void delete(@PathVariable final Long seriesId,
                     @PathVariable final Long seasonId) {
    this.seasonService.delete(seriesId, seasonId);
  }

  
  @PutMapping("/{seasonId}")
  public void put(@PathVariable final Long seriesId,
                  @PathVariable("seasonId") final Long seasonId,
                  @Valid @RequestBody final SeasonDTO seasonDTO) {
    seasonDTO.setId(seasonId);
    this.seasonService.update(seasonDTO);
  }


}
