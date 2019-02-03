package com.questv.api.season;

import com.questv.api.series.SeriesDTO;
import com.questv.api.series.SeriesRepository;
import com.questv.api.contracts.ObjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    final SeasonDTO[] result = new SeasonDTO[1];
    this.seriesRepository.findById(seasonDTO.getSeriesId())
        .ifPresent(seriesModel -> {
          final SeasonModel seasonModel = this.seasonRepository.save(seasonDTO.convert());
          seriesModel.attachSeason(seasonModel);
          result[0] = seasonModel.convert();
          this.seriesRepository.save(seriesModel);
        });
    return result[0];
  }


  @Override
  public SeasonDTO create(final SeasonDTO model) {
    return this.seasonRepository.save(model.convert()).convert();
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
    this.seasonRepository.findById(seasonId)
        .ifPresent((seasonModel) -> {
          this.seriesRepository.findById(seasonModel.getSeriesId())
              .ifPresent((seriesModel) -> {
                seriesModel.removeSeasonById(seasonId);
                this.seriesRepository.save(seriesModel);
                this.seasonRepository.deleteById(seasonId);
              });
        });
  }

  @Override
  public List<SeasonDTO> findAllByParent(final Long seriesId) {
    final List<SeasonDTO> result = new ArrayList<>();

    this.seriesRepository.findById(seriesId).
        ifPresent((seriesModel) -> result.addAll(
            seriesModel
                .getSeasons()
                .stream()
                .map(SeasonModel::convert)
                .collect(Collectors.toList())
        ));
    return result;
  }
}
