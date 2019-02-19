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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service(value = "questionService")
public class QuestionService {

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


  public QuestionDTO createAndAttach(final Long questionableId,
                                     final QuestionDTO questionDTO) {
    final Questionable questionable = findQuestionableById(questionableId);
    questionDTO.setQuestionableId(questionableId);
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

  public Questionable findQuestionableById(final Long questionableId) {
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


  public QuestionDTO create(final QuestionDTO model) {
    throw new RuntimeException("Cannot attach a question into an empty owner.");
  }


  public List<QuestionDTO> findAll() {
    return this.questionRepository
        .findAll()
        .stream()
        .map(QuestionModel::convert)
        .collect(Collectors.toList());
  }


  public QuestionDTO findById(final Long questionId) {
    return findModelById(questionId).convert();
  }

  private QuestionModel findModelById(final Long questionId) {
    final Optional<QuestionModel> foundQuestion = this.questionRepository.findById(questionId);
    if (foundQuestion.isPresent()) {
      return foundQuestion.get();
    } else {
      throw new IdNotFoundException("Cannot find a question with given id.");
    }
  }


  public void update(QuestionDTO questionDTO) {
    final QuestionModel questionModel = findModelById(questionDTO.getId());
    questionModel.update(questionDTO.convert());
    save(questionModel);
  }

  private QuestionModel save(final QuestionModel questionModel) {
    return this.questionRepository.save(questionModel);
  }


  public void delete(final Long questionableId, final Long questionId) {
    final QuestionModel questionModel = findModelById(questionId);
    final Questionable questionable = findQuestionableById(questionableId);
    detachQuestionFromQuestionable(questionModel, questionable);
    this.questionRepository.deleteById(questionId);
  }

  private void detachQuestionFromQuestionable(final QuestionModel questionModel,
                                              final Questionable questionable) {
    questionable.detachQuestion(questionModel);
    saveQuestionable(questionable);
  }


  public List<QuestionDTO> findAllByParent(final Long questionableId) {
    Questionable questionable = findQuestionableById(questionableId);
    return questionable
        .getQuestions()
        .stream()
        .map(QuestionModel::convert)
        .collect(Collectors.toList());
  }

  /*default*/ List<QuestionDTO> findAllByParentRecursive(final Long parentId) {
    return findQuestionableById(parentId)
        .getQuestionsRecursively()
        .stream()
        .map(QuestionModel::convert).distinct().collect(Collectors.toList());
  }
}
