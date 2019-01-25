package com.questv.api.question;

import com.questv.api.contracts.ObjectService;
import com.questv.api.episode.EpisodeRepository;
import com.questv.api.season.SeasonRepository;
import com.questv.api.series.SeriesRepository;

import java.util.List;
import java.util.stream.Collectors;

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


  public void createAndAttach(final QuestionType questionType, final Long superId, final QuestionDTO questionDTO) {
    switch (questionType) {
      case SERIES: {
        attachQuestionIntoSeries(superId, questionDTO);
        break;
      }

      case SEASON: {
        attachQuestionIntoSeason(superId, questionDTO);
        break;
      }

      case EPISODE: {
        attachQuestionIntoEpisode(superId, questionDTO);
        break;
      }

      default:
        break;
    }
  }

  private void attachQuestionIntoEpisode(Long superId, QuestionDTO questionDTO) {
    this.episodeRepository.findById(superId)
        .ifPresent((episodeModel) -> {
          final QuestionModel savedModel = this.questionRepository.save(questionDTO.convert());
          episodeModel.attachQuestion(savedModel);
          this.episodeRepository.save(episodeModel);
        });
  }

  private void attachQuestionIntoSeason(Long superId, QuestionDTO questionDTO) {
    this.seasonRepository.findById(superId)
        .ifPresent((seasonModel) -> {
          final QuestionModel savedModel = this.questionRepository.save(questionDTO.convert());
          seasonModel.attachQuestion(savedModel);
          this.seasonRepository.save(seasonModel);
        });
  }

  private void attachQuestionIntoSeries(final Long seriesId, final QuestionDTO questionDTO) {
    this.seriesRepository.findById(seriesId)
        .ifPresent((seriesModel) -> {
          final QuestionModel savedModel = this.questionRepository.save(questionDTO.convert());
          seriesModel.attachQuestion(savedModel);
          this.seriesRepository.save(seriesModel);
        });
  }

  @Override
  public void createAndAttach(Long superId, QuestionDTO model) {
    throw new RuntimeException("Cannot attach a question into an empty owner.");
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
    throw new RuntimeException("Cannot delete a question with an empty owner.");
  }

  public void deleteById(final QuestionType questionType, final Long questionId) {
    switch (questionType) {
      case SERIES: {
        deleteQuestionFromSeries(questionId);
        break;
      }

      case SEASON: {
        deleteQuestionFromSeason(questionId);
        break;
      }

      case EPISODE: {
        deleteQuestionFromEpisode(questionId);
        break;
      }

      default: break;
    }
  }

  private void deleteQuestionFromEpisode(final Long questionId) {
    this.questionRepository.findById(questionId)
        .ifPresent((questionModel) -> {
          this.episodeRepository.findById(questionModel.getOwnerId())
          .ifPresent((episodeModel) -> {
            episodeModel.detachQuestion(questionModel);
            this.episodeRepository.save(episodeModel);
            this.questionRepository.deleteById(questionId);
          });
        });
  }

  private void deleteQuestionFromSeason(final Long questionId) {
    this.questionRepository.findById(questionId)
        .ifPresent((questionModel) -> {
          this.seasonRepository.findById(questionModel.getOwnerId())
              .ifPresent((seasonModel) -> {
                seasonModel.detachQuestion(questionModel);
                this.seasonRepository.save(seasonModel);
                this.questionRepository.deleteById(questionId);
              });
        });
  }

  private void deleteQuestionFromSeries(final Long questionId) {
    this.questionRepository.findById(questionId)
        .ifPresent((questionModel) -> {
          this.seriesRepository.findById(questionModel.getOwnerId())
              .ifPresent((seriesModel) -> {
                seriesModel.detachQuestion(questionModel);
                this.seriesRepository.save(seriesModel);
                this.questionRepository.deleteById(questionId);
              });
        });

  }
}
