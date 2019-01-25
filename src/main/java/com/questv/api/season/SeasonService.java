package com.questv.api.season;

import com.questv.api.util.ObjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeasonService implements ObjectService<SeasonDTO> {

  private final SeasonRepository seasonRepository;

  public SeasonService(final SeasonRepository seasonRepository) {
    this.seasonRepository = seasonRepository;
    assert this.seasonRepository != null;
  }

  @Override
  public SeasonDTO create(final SeasonDTO userModel) {
    return this.seasonRepository.save(userModel.convert()).convert();
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
  public SeasonDTO findById(final Long seasonId) {
    return this.seasonRepository
        .findById(seasonId)
        .map(SeasonModel::convert)
        .orElse(new NullSeasonDTO());
  }

  @Override
  public void updateById(final Long seasonId, final SeasonDTO seasonDTO) {
    this.seasonRepository.findById(seasonId).ifPresent((seasonModel) -> {
      seasonModel.update(seasonDTO.convert());
      this.seasonRepository.save(seasonModel);
    });

  }

  @Override
  public void deleteById(final Long seasonId) {
    this.seasonRepository.deleteById(seasonId);
  }
}
