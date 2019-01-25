package com.questv.api.episode;

import com.questv.api.season.SeasonRepository;
import com.questv.api.contracts.ObjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
  public void createAndAttach(final EpisodeDTO episodeDTO) {
    this.seasonRepository.findById(episodeDTO.getSeasonId())
        .ifPresent((seasonModel) -> {
          final EpisodeModel episodeModel = this.episodeRepository.save(episodeDTO.convert());
          seasonModel.attachEpisode(episodeModel);
          this.seasonRepository.save(seasonModel);
        });
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
    return this.episodeRepository.findById(episodeId)
        .map(EpisodeModel::convert)
        .orElse(new NullEpisodeDTO());
  }

  @Override
  public void updateById(final Long episodeId, final EpisodeDTO episodeDTO) {
    this.episodeRepository.findById(episodeId)
        .ifPresent((episodeModel) -> {
          episodeModel.update(episodeDTO.convert());
          this.episodeRepository.save(episodeModel);
        });
  }

  @Override
  public void deleteById(final Long episodeId) {
    this.episodeRepository.findById(episodeId)
        .ifPresent((episodeModel) -> {
          this.seasonRepository.findById(episodeModel.getSeasonId())
              .ifPresent((seasonModel) -> {
                seasonModel.removeEpisodeById(episodeId);
                this.seasonRepository.save(seasonModel);
                this.episodeRepository.deleteById(episodeId);
              });
        });
  }
}
