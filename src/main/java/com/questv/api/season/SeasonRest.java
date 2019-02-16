package com.questv.api.season;

import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Restable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  @PostMapping("/seasons")
  public ResponseEntity<SeasonDTO> post(@Valid @RequestBody final SeasonDTO seasonDTO) {
    try {
      SeasonDTO attachedSeason = this.seasonService.createAndAttach(seasonDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(attachedSeason);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/series/{seriesId}/seasons")
  public ResponseEntity<List<SeasonDTO>> getAllBySeries(@PathVariable final String seriesId) {
    try {
      final List<SeasonDTO> allByParent = this.seasonService.findAllByParent(seriesId);
      return ResponseEntity.ok(allByParent);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @Override
  @GetMapping("/seasons")
  public ResponseEntity<List<SeasonDTO>> get() {
    return ResponseEntity.ok(this.seasonService.findAll());
  }

  @Override
  @GetMapping("/seasons/{seasonId}")
  public ResponseEntity<SeasonDTO> get(@PathVariable final String seasonId) {
    try {
      return ResponseEntity.ok(this.seasonService.findById(seasonId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @Override
  @DeleteMapping("/seasons/{seasonId}")
  public void delete(@PathVariable final String seasonId) {
    this.seasonService.delete(seasonId);
  }

  @Override
  @PutMapping("/seasons/{seasonId}")
  public void put(@PathVariable("seasonId") final String seasonId, @Valid @RequestBody final SeasonDTO seasonDTO) {
    seasonDTO.setId(seasonId);
    this.seasonService.update(seasonDTO);
  }


}
