package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SeriesRest {
  private final ObjectService<SeriesDTO> seriesService;

  public SeriesRest(final ObjectService<SeriesDTO> seriesService) {
    this.seriesService = seriesService;
  }

  @PostMapping("/series")
  @ResponseStatus(value = HttpStatus.CREATED)
  @ResponseBody
  public SeriesDTO postSeries(@Valid @RequestBody final SeriesDTO seriesDTO) {
    return this.seriesService.create(seriesDTO);
  }

  @GetMapping("/series")
  public List<SeriesDTO> getSeries() {
    return this.seriesService.findAll();
  }

  @GetMapping("/series/{seriesId}")
  public SeriesDTO getSeriesById(@PathVariable Long seriesId) {
    return this.seriesService.findById(seriesId);
  }

  @PutMapping("/series/{seriesId}")
  public void putSeries(@PathVariable final Long seriesId, @RequestBody final SeriesDTO seriesDTO) {
    this.seriesService.updateById(seriesId, seriesDTO);
  }

  @DeleteMapping("/series/{seriesId}")
  public void deleteSeries(@PathVariable final Long seriesId) {
    this.seriesService.deleteById(seriesId);
  }

}
