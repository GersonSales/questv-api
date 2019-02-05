package com.questv.api.question;

import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Questionable;
import com.questv.api.episode.EpisodeModel;
import com.questv.api.episode.EpisodeRepository;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.season.SeasonModel;
import com.questv.api.season.SeasonRepository;
import com.questv.api.series.SeriesModel;
import com.questv.api.series.SeriesRepository;
import org.jetbrains.annotations.NotNull;
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
    final Questionable questionable = findQuestionableById(questionDTO.getOwnerId());
    final QuestionModel questionModel = save(questionDTO.convert());
    questionable.attachQuestion(questionModel);
    saveQuestionable(questionable);
    return questionModel.convert();
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

  @NotNull
  private Questionable findQuestionableById(final Long questionableId) {
    Optional<SeriesModel> seriesById = this.seriesRepository.findById(questionableId);
    if (seriesById.isPresent()) {
      return seriesById.get();
    }

    Optional<SeasonModel> seasonById = this.seasonRepository.findById(questionableId);
    if (seasonById.isPresent()) {
      return seasonById.get();
    }

    Optional<EpisodeModel> episodeById = this.episodeRepository.findById(questionableId);
    if (episodeById.isPresent()) {
      return episodeById.get();
    }

    throw new IdNotFoundException("Cannot find the question owner with this given id");
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
    return findModelById(questionId).convert();
  }

  @NotNull
  private QuestionModel findModelById(final Long questionId) {
    final Optional<QuestionModel> foundQuestion = this.questionRepository.findById(questionId);
    if (foundQuestion.isPresent()) {
      return foundQuestion.get();
    } else {
      throw new IdNotFoundException("Cannot find a question with given id.");
    }
  }

  @Override
  public void update(QuestionDTO questionDTO) {
    final QuestionModel questionModel = findModelById(questionDTO.getId());
    questionModel.update(questionDTO.convert());
    save(questionModel);
  }

  private QuestionModel save(final QuestionModel questionModel) {
    return this.questionRepository.save(questionModel);
  }


  @Override
  public void delete(final Long questionId) {
    final QuestionModel questionModel = findModelById(questionId);
    final Questionable questionable = findQuestionableById(questionModel.getOwnerId());
    detachQuestionFromQuestionable(questionModel, questionable);
    this.questionRepository.deleteById(questionId);
  }

  private void detachQuestionFromQuestionable(final QuestionModel questionModel,
                                              @NotNull final Questionable questionable) {
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

  /*default*/ List<QuestionDTO> findAllByParentRecursive(final Long parentId) {
    return findQuestionableById(parentId)
        .getQuestionsRecursively()
        .stream()
        .map(QuestionModel::convert).distinct().collect(Collectors.toList());
  }
}
