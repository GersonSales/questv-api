package com.questv.api.analytics;

import com.questv.api.analytics.model.AnsweredCategory;
import com.questv.api.analytics.model.AnsweredItem;
import com.questv.api.contracts.Questionable;
import com.questv.api.episode.EpisodeModel;
import com.questv.api.episode.EpisodeService;
import com.questv.api.question.QuestionDTO;
import com.questv.api.question.QuestionService;
import com.questv.api.season.SeasonDTO;
import com.questv.api.season.SeasonModel;
import com.questv.api.season.SeasonService;
import com.questv.api.series.SeriesModel;
import com.questv.api.series.SeriesService;
import com.questv.api.user.UserDTO;
import com.questv.api.user.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "analyticsService ")
public class AnalyticsService {
  private final UserService userService;
  private final SeriesService seriesService;
  private final SeasonService seasonService;
  private final EpisodeService episodeService;
  private final QuestionService questionService;

  public AnalyticsService(final UserService userService,
                          final SeriesService seriesService,
                          final SeasonService seasonService,
                          final EpisodeService episodeService,
                          final QuestionService questionService) {
    this.userService = userService;
    this.seriesService = seriesService;
    this.seasonService = seasonService;
    this.episodeService = episodeService;
    this.questionService = questionService;
    assert this.userService != null;
    assert this.seriesService != null;
    assert this.seasonService != null;
    assert this.episodeService != null;
    assert this.questionService != null;
  }


  public AnalyticsDTO getUserAnalytics(final String userId) {
    final UserDTO userDTO = this.userService.findById(userId);
    final AnalyticsDTO result = new AnalyticsDTO();

    result.setAnsweredSeries(getAnsweredSeries(userDTO));
    result.setAnsweredCategories(getAnsweredCategories(userDTO));

    final Integer totalOfAnsweredQuestions = userDTO.getAnsweredQuestionsCount();
    result.setTotalOfAnsweredQuestions(totalOfAnsweredQuestions);

    final Integer correctAnsweredQuestionsCount = getCorrectAnsweredQuestionsCount(userDTO);
    result.setCorrectAnsweredQuestions(correctAnsweredQuestionsCount);

    result.setWrongAnsweredQuestions(totalOfAnsweredQuestions - correctAnsweredQuestionsCount);


    return result;

  }

  private Integer getCorrectAnsweredQuestionsCount(final UserDTO userDTO) {
    Integer result = 0;

    final Map<Long, Long> answeredQuestions = userDTO.getAnsweredQuestions();
    for (final Long questionId : answeredQuestions.keySet()) {
      final QuestionDTO questionDTO = questionService.findById(questionId);
      if (questionDTO.getCorrectAnswerId().equals(answeredQuestions.get(questionId))) {
        result++;
      }
    }

    return result;
  }


  private List<AnsweredItem> getAnsweredCategories(final UserDTO userDTO) {
    final Map<String, AnsweredItem> answeredCategoriesMap = new HashMap<>();
    populateAnsweredMap(userDTO, answeredCategoriesMap);
    return new ArrayList<>(answeredCategoriesMap.values());
  }


  private List<AnsweredItem> getAnsweredSeries(final UserDTO userDTO) {
    final Map<String, AnsweredItem> answeredSeriesMap = new HashMap<>();
    populateAnsweredMap(userDTO, answeredSeriesMap);
    return new ArrayList<>(answeredSeriesMap.values());
  }

  private void populateAnsweredMap(UserDTO userDTO, Map<String, AnsweredItem> answeredSeriesMap) {
    final Map<Long, Long> answeredQuestions = userDTO.getAnsweredQuestions();
    for (final Long questionId : answeredQuestions.keySet()) {
      final QuestionDTO questionModel = this.questionService.findById(questionId);
      final SeriesModel seriesModel = getSeasonModel(questionModel);

      final String seriesName = seriesModel.getName();
      Long userAnswer = answeredQuestions.get(questionId);

      updateAnsweredMap(answeredSeriesMap,
          userAnswer,
          questionModel,
          seriesName);
    }
  }

  private void updateAnsweredMap(final Map<String, AnsweredItem> answeredSeriesMap,
                                 final Long userAnswer,
                                 final QuestionDTO questionModel,
                                 final String key) {

    if (answeredSeriesMap.containsKey(key)) {
      if (questionModel.getCorrectAnswerId().equals(userAnswer)) {
        answeredSeriesMap.get(key).IncrementCorrectAnswersCount();
      } else {
        answeredSeriesMap.get(key).IncrementWrongAnswersCount();
      }
    } else {
      if (questionModel.getCorrectAnswerId().equals(userAnswer)) {
        final AnsweredItem answeredItem = new AnsweredItem(key);
        answeredItem.IncrementCorrectAnswersCount();
        answeredSeriesMap.put(key, answeredItem);
      } else {
        final AnsweredItem answeredItem = new AnsweredItem(key);
        answeredItem.IncrementWrongAnswersCount();
        answeredSeriesMap.put(key, answeredItem);
      }
    }
  }

  private SeriesModel getSeasonModel(final QuestionDTO questionModel) {
    Questionable questionable = this.questionService.findQuestionableById(questionModel.getQuestionableId());
    if (questionable instanceof EpisodeModel) {
      final SeasonDTO seasonModel = this.seasonService.findById(((EpisodeModel) questionable).getOwnerId());
      questionable = this.seriesService.findModelById(seasonModel.getOwnerId());
    } else if (questionable instanceof SeasonModel) {
      questionable = this.seriesService.findModelById(((SeasonModel) questionable).getOwnerId());
    }
    return (SeriesModel) questionable;
  }
}
