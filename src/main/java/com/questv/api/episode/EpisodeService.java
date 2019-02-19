package com.questv.api.episode;

import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.EpisodeNotFoundException;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.exception.SeasonNotFoundException;
import com.questv.api.season.SeasonModel;
import com.questv.api.season.SeasonRepository;
import com.questv.api.series.SeriesModel;
import com.questv.api.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service(value = "episodeService")
public class EpisodeService {

  private final EpisodeRepository episodeRepository;
  private final SeasonRepository seasonRepository;
  private final SeriesRepository seriesRepository;

  public EpisodeService(final EpisodeRepository episodeRepository,
                        final SeasonRepository seasonRepository,
                        final SeriesRepository seriesRepository) {
    this.episodeRepository = episodeRepository;
    this.seasonRepository = seasonRepository;
    this.seriesRepository = seriesRepository;
    assert this.episodeRepository != null;
    assert this.seasonRepository != null;
    assert this.seriesRepository != null;
  }


  public EpisodeDTO createAndAttach(final Long seriesId,
                                    final Integer seasonNumber,
                                    final EpisodeDTO episodeDTO) {
    final SeasonModel seasonModel = findSeasonByNumber(seriesId, seasonNumber);
    final EpisodeModel episodeModel = save(episodeDTO.convert());
    seasonModel.attachEpisode(episodeModel);
    this.seasonRepository.save(seasonModel);
    return episodeModel.convert();
  }


  public EpisodeDTO create(final EpisodeDTO episodeDTO) {
    return this.episodeRepository.save(episodeDTO.convert()).convert();
  }


  public List<EpisodeDTO> findAll() {
    final List<EpisodeModel> result = new ArrayList<>();
    this.episodeRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(EpisodeModel::convert)
        .collect(Collectors.toList());
  }


  public EpisodeDTO findById(final Long episodeId) {
    return findModelById(episodeId).convert();
  }

  public EpisodeDTO findByNumber(final Long seriesId,
                                 final Integer seasonNumber,
                                 final Integer episodeNumber) {
    return findModelByNumber(seriesId, seasonNumber, episodeNumber).convert();
  }

  public EpisodeModel findModelByNumber(final Long seriesId,
                                        final Integer seasonNumber,
                                        final Integer episodeNumber) {

    return findSeasonByNumber(seriesId, seasonNumber).getEpisodesByNumber(episodeNumber);
  }


  private EpisodeModel findModelById(final Long seasonId,
                                     final Integer episodeNumber) {
    final Optional<SeasonModel> optSeasonModel = this.seasonRepository.findById(seasonId);
    if (optSeasonModel.isPresent()) {
      final SeasonModel seasonModel = optSeasonModel.get();
      return seasonModel.getEpisodesByNumber(episodeNumber);
    }

    throw new EpisodeNotFoundException();
  }


  private EpisodeModel findModelById(Long episodeId) {
    Optional<EpisodeModel> episodeModel = this.episodeRepository.findById(episodeId);
    if (episodeModel.isPresent()) {
      return episodeModel.get();
    }
    throw new IdNotFoundException();
  }


  public void update(final EpisodeDTO episodeDTO) {
    final EpisodeModel episodeModel = findModelById(episodeDTO.getId());
    episodeModel.update(episodeDTO.convert());
    save(episodeModel);
  }

  public void update(final Long seriesId,
                     final Integer seasonNumber,
                     final Integer episodeNumber,
                     final EpisodeDTO episodeDTO) {
    final EpisodeModel episodeModel = findModelByNumber(seriesId, seasonNumber, episodeNumber);
    episodeModel.update(episodeDTO.convert());
    save(episodeModel);
  }

  private EpisodeModel save(final EpisodeModel episodeModel) {
    return this.episodeRepository.save(episodeModel);
  }

  private SeasonModel findSeasonByNumber(final Long seriesId,
                                         final Integer seasonNumber) {

    final Optional<SeriesModel> optSeriesModel = this.seriesRepository.findById(seriesId);
    if (optSeriesModel.isPresent()) {
      return optSeriesModel.get().getSeasonByNumber(seasonNumber);
    }
    throw new SeasonNotFoundException();
  }

  private SeasonModel findSeasonById(final Long seasonId) {
    final Optional<SeasonModel> seasonModel = this.seasonRepository.findById(seasonId);
    if (seasonModel.isPresent()) {
      return seasonModel.get();
    }
    throw new IdNotFoundException();
  }


  public void delete(final Long seasonId, final Long episodeId) {
    final SeasonModel seasonModel = findSeasonById(seasonId);
    seasonModel.removeEpisodeById(episodeId);
    this.seasonRepository.save(seasonModel);
    this.episodeRepository.deleteById(episodeId);
  }

  public void delete(final Long seriesId,
                     final Integer seasonNumber,
                     final Integer episodeNumber) {

    final SeasonModel seasonModel = findSeasonByNumber(seriesId, seasonNumber);
    final EpisodeModel episodeModel = seasonModel.getEpisodesByNumber(episodeNumber);
    final Long episodeId = episodeModel.getId();

    seasonModel.removeEpisodeById(episodeId);
    this.seasonRepository.save(seasonModel);
    this.episodeRepository.deleteById(episodeId);
  }


  public List<EpisodeDTO> findAllByParent(final Long seasonId) {
    final SeasonModel seasonModel = findSeasonById(seasonId);
    return seasonModel
        .getEpisodes()
        .stream()
        .map(EpisodeModel::convert)
        .distinct()
        .collect(Collectors.toList());
  }

  public List<EpisodeDTO> findAllByParent(final Long seriesId,
                                          final Integer seasonNumber) {
    return findSeasonByNumber(seriesId, seasonNumber)
        .getEpisodes()
        .stream()
        .map(EpisodeModel::convert)
        .collect(Collectors.toList());
  }
}