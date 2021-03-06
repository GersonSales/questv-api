package com.questv.api.season;

import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.exception.SeasonNotFoundException;
import com.questv.api.series.SeriesModel;
import com.questv.api.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "seasonService")
public class SeasonService {

  private final SeasonRepository seasonRepository;
  private final SeriesRepository seriesRepository;

  public SeasonService(final SeasonRepository seasonRepository,
                       final SeriesRepository seriesRepository) {
    this.seasonRepository = seasonRepository;
    this.seriesRepository = seriesRepository;
    assert this.seasonRepository != null;
    assert this.seriesRepository != null;
  }


  
  public SeasonDTO createAndAttach(final Long seriesId,
                                   final SeasonDTO seasonDTO) {
    final SeriesModel seriesModel = findSeriesById(seriesId);
    seasonDTO.setOwnerId(seriesModel.getId());
    final SeasonModel seasonModel = save(seasonDTO.convert());
    seriesModel.attachSeason(seasonModel);
    saveSeries(seriesModel);
    return seasonModel.convert();
  }

  private SeriesModel findSeriesById(final Long seriesId) {
    Optional<SeriesModel> foundSeries = this.seriesRepository.findById(seriesId);
    if (foundSeries.isPresent()) {
      return foundSeries.get();
    }

    throw new IdNotFoundException();
  }

  private SeriesModel saveSeries(SeriesModel seriesModel) {
    return this.seriesRepository.save(seriesModel);
  }


  
  public SeasonDTO create(final SeasonDTO model) {
    return save(model.convert()).convert();
  }

  
  public List<SeasonDTO> findAll() {
    final List<SeasonModel> result = new ArrayList<>();
    this.seasonRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(SeasonModel::convert)
        .collect(Collectors.toList());
  }

  
  public SeasonDTO findById(final Long seasonId) {
    return findModelById(seasonId).convert();
  }

  public SeasonDTO findByNumber(final Long seriesId, final Integer seasonNumber) {
    return findModelByNumber(seriesId, seasonNumber).convert();
  }

  private SeasonModel findModelByNumber(final Long seriesId,
                                        final Integer seasonNumber) {
    final Optional<SeriesModel> optSeriesModel = this.seriesRepository.findById(seriesId);
    if (optSeriesModel.isPresent()) {
      final SeriesModel seriesModel = optSeriesModel.get();
      return seriesModel.getSeasonByNumber(seasonNumber);
    }


    throw new SeasonNotFoundException();
  }

  private SeasonModel findModelById(final Long seasonId) {
    final Optional<SeasonModel> foundSeason = this.seasonRepository.findById(seasonId);
    if (foundSeason.isPresent()) {
      return foundSeason.get();
    }
    throw new IdNotFoundException();
  }

  
  public void update(final SeasonDTO seasonDTO) {
    final SeasonModel seasonModel = findModelById(seasonDTO.getId());
    seasonModel.update(seasonDTO.convert());
    save(seasonModel);
  }

  private SeasonModel save(final SeasonModel seasonModel) {
    return this.seasonRepository.save(seasonModel);
  }

  
  public void delete(final Long seriesId, final Long seasonId) {
    final SeriesModel seriesModel = findSeriesById(seriesId);

    seriesModel.removeSeasonById(seasonId);
    saveSeries(seriesModel);
    this.seasonRepository.deleteById(seasonId);
  }

  
  public List<SeasonDTO> findAllByParent(final Long seriesId) {
    final SeriesModel seriesModel = findSeriesById(seriesId);
    return seriesModel
        .getSeasons()
        .stream()
        .map(SeasonModel::convert)
        .collect(Collectors.toList());
  }
}
