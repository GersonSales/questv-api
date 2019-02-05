package com.questv.api.episode;

import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.season.SeasonModel;
import com.questv.api.season.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service(value = "episodeService")
public class EpisodeService implements ObjectService<EpisodeDTO> {

  private final EpisodeRepository episodeRepository;
  private final SeasonRepository seasonRepository;

  public EpisodeService(final EpisodeRepository episodeRepository,
                        final SeasonRepository seasonRepository) {
    this.episodeRepository = episodeRepository;
    this.seasonRepository = seasonRepository;
    assert this.episodeRepository != null;
    assert this.seasonRepository != null;
  }


  @Override
  public EpisodeDTO createAndAttach(final EpisodeDTO episodeDTO) {
    final SeasonModel seasonModel = findSeasonById(episodeDTO.getSeasonId());
    final EpisodeModel episodeModel = save(episodeDTO.convert());
    seasonModel.attachEpisode(episodeModel);
    this.seasonRepository.save(seasonModel);
    return episodeModel.convert();
  }

  @Override
  public EpisodeDTO create(final EpisodeDTO episodeDTO) {
    return this.episodeRepository.save(episodeDTO.convert()).convert();
  }

  @Override
  public List<EpisodeDTO> findAll() {
    final List<EpisodeModel> result = new ArrayList<>();
    this.episodeRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(EpisodeModel::convert)
        .collect(Collectors.toList());
  }

  @Override
  public EpisodeDTO findById(Long episodeId) {
    return findModelById(episodeId).convert();
  }

  private EpisodeModel findModelById(Long episodeId) {
    Optional<EpisodeModel> episodeModel = this.episodeRepository.findById(episodeId);
    if (episodeModel.isPresent()) {
      return episodeModel.get();
    }
    throw new IdNotFoundException();
  }

  @Override
  public void update(final EpisodeDTO episodeDTO) {
    final EpisodeModel episodeModel = findModelById(episodeDTO.getId());
    episodeModel.update(episodeDTO.convert());
    save(episodeModel);
  }

  private EpisodeModel save(final EpisodeModel episodeModel) {
    return this.episodeRepository.save(episodeModel);
  }


  private SeasonModel findSeasonById(final Long seasonId) {
    final Optional<SeasonModel> seasonModel = this.seasonRepository.findById(seasonId);
    if (seasonModel.isPresent()) {
      return seasonModel.get();
    }
    throw new IdNotFoundException();
  }

  @Override
  public void delete(final Long episodeId) {
    final EpisodeModel episodeModel = findModelById(episodeId);
    final SeasonModel seasonModel = findSeasonById(episodeModel.getSeasonId());
    seasonModel.removeEpisodeById(episodeId);
    this.seasonRepository.save(seasonModel);
    this.episodeRepository.deleteById(episodeId);
  }

  @Override
  public List<EpisodeDTO> findAllByParent(final Long seasonId) {
    final SeasonModel seasonModel = findSeasonById(seasonId);
    return seasonModel
        .getEpisodes()
        .stream()
        .map(EpisodeModel::convert)
        .distinct()
        .collect(Collectors.toList());
  }
}
