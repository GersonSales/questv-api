package com.questv.api.series;

import com.questv.api.util.ObjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeriesService implements ObjectService<SeriesDTO> {

  private final SeriesRepository seriesRepository;

  public SeriesService(final SeriesRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
    assert this.seriesRepository != null;
  }

  @Override
  public SeriesDTO create(final SeriesDTO seriesDTO) {
    return this.seriesRepository.save(seriesDTO.convert()).convert();
  }

  @Override
  public List<SeriesDTO> findAll() {
    final List<SeriesModel> result = new ArrayList<>();
    this.seriesRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(SeriesModel::convert)
        .collect(Collectors.toList());
  }

  @Override
  public SeriesDTO findById(final Long seriesId) {
    return this.seriesRepository
        .findById(seriesId)
        .map(SeriesModel::convert)
        .orElse(new NullSeriesDTO());
  }

  @Override
  public void updateById(final Long seriesId, final SeriesDTO seriesDTO) {
    final SeriesDTO foundSeries = this.findById(seriesId);
    foundSeries.update(seriesDTO);
    this.seriesRepository.save(foundSeries.convert());
  }

  @Override
  public void deleteById(final Long seriesId) {
    this.seriesRepository.deleteById(seriesId);
  }
}
