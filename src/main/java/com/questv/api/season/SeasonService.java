package com.questv.api.season;

import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.series.SeriesModel;
import com.questv.api.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "seasonService")
public class SeasonService implements ObjectService<SeasonDTO> {

  private final SeasonRepository seasonRepository;
  private final SeriesRepository seriesRepository;

  public SeasonService(final SeasonRepository seasonRepository,
                       final SeriesRepository seriesRepository) {
    this.seasonRepository = seasonRepository;
    this.seriesRepository = seriesRepository;
    assert this.seasonRepository != null;
    assert this.seriesRepository != null;
  }


  @Override
  public SeasonDTO createAndAttach(final SeasonDTO seasonDTO) {
    final String seriesId = seasonDTO.getSeriesId();
    final SeriesModel seriesModel = findSeriesById(seriesId);
    final SeasonModel seasonModel = save(seasonDTO.convert());
    seriesModel.attachSeason(seasonModel);
    saveSeries(seriesModel);
    return seasonModel.convert();
  }

  private SeriesModel findSeriesById(final String seriesId) {
    Optional<SeriesModel> foundSeries = this.seriesRepository.findById(seriesId);
    if (foundSeries.isPresent()) {
      return foundSeries.get();
    }

    throw new IdNotFoundException();
  }

  private SeriesModel saveSeries(SeriesModel seriesModel) {
    return this.seriesRepository.save(seriesModel);
  }


  @Override
  public SeasonDTO create(final SeasonDTO model) {
    return save(model.convert()).convert();
  }

  @Override
  public List<SeasonDTO> findAll() {
    final List<SeasonModel> result = new ArrayList<>();
    this.seasonRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(SeasonModel::convert)
        .collect(Collectors.toList());
  }

  @Override
  public SeasonDTO findById(final String seasonId) {
    return findModelById(seasonId).convert();
  }

  private SeasonModel findModelById(final String seasonId) {
    final Optional<SeasonModel> foundSeason = this.seasonRepository.findById(seasonId);
    if (foundSeason.isPresent()) {
      return foundSeason.get();
    }
    throw new IdNotFoundException();
  }

  @Override
  public void update(final SeasonDTO seasonDTO) {
    final SeasonModel seasonModel = findModelById(seasonDTO.getId());
    seasonModel.update(seasonDTO.convert());
    save(seasonModel);
  }

  private SeasonModel save(final SeasonModel seasonModel) {
    return this.seasonRepository.save(seasonModel);
  }

  @Override
  public void delete(final String seasonId) {
    final SeasonModel seasonModel = findModelById(seasonId);
    final SeriesModel seriesModel = findSeriesById(seasonModel.getSeriesId());

    seriesModel.removeSeasonById(seasonId);
    saveSeries(seriesModel);
    this.seasonRepository.deleteById(seasonId);
  }

  @Override
  public List<SeasonDTO> findAllByParent(final String seriesId) {
    final SeriesModel seriesModel = findSeriesById(seriesId);
    return seriesModel
        .getSeasons()
        .stream()
        .map(SeasonModel::convert)
        .collect(Collectors.toList());
  }
}
