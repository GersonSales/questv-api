package com.questv.api.series;

import com.questv.api.util.ObjectService;

import java.util.List;

public class SeriesRest {
  private final ObjectService<SeriesDTO> seriesService;

  public SeriesRest(final ObjectService<SeriesDTO> seriesService) {
    this.seriesService = seriesService;
  }

  public void postSeries(final SeriesDTO seriesDTO) {
    this.seriesService.create(seriesDTO);
  }

  public List<SeriesDTO> getSeries() {
    return this.seriesService.findAll();
  }

  public SeriesDTO getSeriesById(final long id) {
    return this.seriesService.findById(id);
  }

  public void putSeries(final Long seriesId, final SeriesDTO seriesDTO) {
    this.seriesService.updateById(seriesId, seriesDTO);
  }

  public void deleteSeries(final Long seriesId) {
    this.seriesService.deleteById(seriesId);
  }


}
