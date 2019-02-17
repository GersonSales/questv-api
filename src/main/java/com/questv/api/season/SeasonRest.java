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
  private final ObjectService<SeasonDTO> seasonService;

  public SeasonRest(final ObjectService<SeasonDTO> seasonService) {
    this.seasonService = seasonService;
    assert this.seasonService != null;
  }

  @PostMapping()
  public ResponseEntity<SeasonDTO> post(@PathVariable("seriesId") final String seriesId,
                                        @Valid @RequestBody final SeasonDTO seasonDTO) {
    try {
      SeasonDTO attachedSeason = this.seasonService.createAndAttach(seasonDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(attachedSeason);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping()
  public ResponseEntity<List<SeasonDTO>> getAllBySeries(@PathVariable final String seriesId) {
    try {
      final List<SeasonDTO> allByParent = this.seasonService.findAllByParent(seriesId);
      return ResponseEntity.ok(allByParent);
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  public ResponseEntity<List<SeasonDTO>> get(@PathVariable final String seriesId) {
    return ResponseEntity.ok(this.seasonService.findAll());
  }

  
  @GetMapping("/{seasonId}")
  public ResponseEntity<SeasonDTO> get(@PathVariable final String seriesId,
                                       @PathVariable final String seasonId) {
    try {
      return ResponseEntity.ok(this.seasonService.findById(seasonId));
    } catch (final Exception exception) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  
  @DeleteMapping("/{seasonId}")
  public void delete(@PathVariable final String seriesId,
                     @PathVariable final String seasonId) {
    this.seasonService.delete(seasonId);
  }

  
  @PutMapping("/{seasonId}")
  public void put(@PathVariable final String seriesId,
                  @PathVariable("seasonId") final String seasonId,
                  @Valid @RequestBody final SeasonDTO seasonDTO) {
    seasonDTO.setId(seasonId);
    this.seasonService.update(seasonDTO);
  }


}
