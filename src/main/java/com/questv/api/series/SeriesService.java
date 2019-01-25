package com.questv.api.series;

import com.questv.api.util.ObjectService;
import com.questv.api.user.UserDTO;

import java.util.List;

public class SeriesService implements ObjectService<SeriesDTO> {

  private final SeriesRepository seriesRepository;

  public SeriesService(final SeriesRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
  }

  @Override
  public SeriesDTO create(final SeriesDTO userModel) {
    return null;
  }

  @Override
  public List<SeriesDTO> findAll() {
    return null;
  }

  @Override
  public SeriesDTO findById(final Long tId) {
    return null;
  }

  @Override
  public void updateById(final Long tId, final SeriesDTO seriesDTO) {

  }

  @Override
  public void deleteById(final Long tId) {

  }
}
