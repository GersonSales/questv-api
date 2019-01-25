package com.questv.api.episode;

import com.questv.api.season.SeasonRepository;
import com.questv.api.season.SeasonService;
import com.questv.api.util.ObjectService;

import java.util.List;

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
  public void createAndAttach(final Long superId, final EpisodeDTO episodeDTO) {
    this.seasonRepository.findById(superId)
        .ifPresent((seasonModel) -> {
          final EpisodeModel episodeModel = this.episodeRepository.save(episodeDTO.convert());
          seasonModel.attachEpisode(episodeModel);
          this.seasonRepository.save(seasonModel);
        });

  }

  @Override
  public EpisodeDTO create(EpisodeDTO model) {
    return null;
  }

  @Override
  public List<EpisodeDTO> findAll() {
    return null;
  }

  @Override
  public EpisodeDTO findById(Long tId) {
    return null;
  }

  @Override
  public void updateById(Long tId, EpisodeDTO episodeDTO) {

  }

  @Override
  public void deleteById(Long tId) {

  }
}
