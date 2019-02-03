package com.questv.api.question;

import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Questionable;
import com.questv.api.episode.EpisodeModel;
import com.questv.api.episode.EpisodeRepository;
import com.questv.api.season.SeasonModel;
import com.questv.api.season.SeasonRepository;
import com.questv.api.series.SeriesModel;
import com.questv.api.series.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service(value = "questionService")
public class QuestionService implements ObjectService<QuestionDTO> {

  private final QuestionRepository questionRepository;
  private final SeriesRepository seriesRepository;
  private final SeasonRepository seasonRepository;
  private final EpisodeRepository episodeRepository;


  public QuestionService(final QuestionRepository questionRepository,
                         final SeriesRepository seriesRepository,
                         final SeasonRepository seasonRepository,
                         final EpisodeRepository episodeRepository) {
    this.questionRepository = questionRepository;
    this.seriesRepository = seriesRepository;
    this.seasonRepository = seasonRepository;
    this.episodeRepository = episodeRepository;
    assert this.questionRepository != null;
  }

  @Override
  public QuestionDTO createAndAttach(final QuestionDTO questionDTO) {
    QuestionDTO result = null;
    final Long ownerId = questionDTO.getOwnerId();
    final Questionable questionable = getQuestionableById(ownerId);
    if (questionable != null) {
      QuestionModel questionModel = this.questionRepository.save(questionDTO.convert());
      questionable.attachQuestion(questionModel);
      result = questionModel.convert();
      saveQuestionable(questionable);
    }
    return result;
  }

  private void saveQuestionable(final Questionable questionable) {
    if (questionable instanceof SeriesModel) {
      this.seriesRepository.save((SeriesModel) questionable);
    } else if (questionable instanceof SeasonModel) {
      this.seasonRepository.save((SeasonModel) questionable);
    } else if (questionable instanceof EpisodeModel) {
      this.episodeRepository.save((EpisodeModel) questionable);
    }
  }

  private Questionable getQuestionableById(final Long questionableId) {
    Optional<SeriesModel> seriesById = this.seriesRepository.findById(questionableId);
    if (seriesById.isPresent()) {
      return seriesById.get();
    }

    Optional<SeasonModel> seasonById = this.seasonRepository.findById(questionableId);
    if (seasonById.isPresent()) {
      return seasonById.get();
    }

    Optional<EpisodeModel> episodeById = this.episodeRepository.findById(questionableId);
    return episodeById.orElse(null);

  }

  @Override
  public QuestionDTO create(final QuestionDTO model) {
    throw new RuntimeException("Cannot attach a question into an empty owner.");
  }

  @Override
  public List<QuestionDTO> findAll() {
    return this.questionRepository
        .findAll()
        .stream()
        .map(QuestionModel::convert)
        .collect(Collectors.toList());
  }

  @Override
  public QuestionDTO findById(final Long questionId) {
    return this.questionRepository.findById(questionId)
        .map(QuestionModel::convert)
        .orElse(new NullQuestionDTO());
  }

  @Override
  public void updateById(final Long questionId, QuestionDTO questionDTO) {
    this.questionRepository
        .findById(questionId)
        .ifPresent((questionModel) -> {
          questionModel.update(questionDTO.convert());
          this.questionRepository.save(questionModel);
        });
  }

  @Override
  public void deleteById(final Long questionId) {
    this.questionRepository.findById(questionId)
        .ifPresent((questionModel) -> detachQuestionFromQuestionable(questionModel,
            getQuestionableById(questionModel.getOwnerId())));
    this.questionRepository.deleteById(questionId);
  }

  private void detachQuestionFromQuestionable(final QuestionModel questionModel,
                                              final Questionable questionable) {
    questionable.detachQuestion(questionModel);
    saveQuestionable(questionable);
  }

  @Override
  public List<QuestionDTO> findAllByParent(Long parentId) {
    final List<QuestionDTO> result = new ArrayList<>();
    for (final QuestionDTO questionDTO : this.findAll()) {
      if (questionDTO.getOwnerId().equals(parentId)) {
        result.add(questionDTO);
      }
    }
    return result;
  }
}
