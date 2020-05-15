package com.questv.api.season;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.questv.api.uitl.Strings.*;


@Api(
    value = SEASON_API_NAME,
    description = SEASON_API_DESCRIPTION,
    tags = {SEASON_API_NAME})
@RestController
@RequestMapping(API_ENDPOINT + "/series/{seriesId}/seasons")
public class SeasonController {
  private final SeasonService seasonService;

  public SeasonController(final SeasonService seasonService) {
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
  
  @GetMapping("/{seasonNumber}")
  public ResponseEntity<SeasonDTO> get(@PathVariable final Long seriesId,
                                       @PathVariable final Integer seasonNumber) {
    try {
      return ResponseEntity.ok(this.seasonService.findByNumber(seriesId, seasonNumber));
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
