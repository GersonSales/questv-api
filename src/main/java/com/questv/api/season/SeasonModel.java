package com.questv.api.season;

import com.questv.api.contracts.Questionable;
import com.questv.api.episode.EpisodeModel;
import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;
import com.questv.api.question.QuestionModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Embeddable
@Table(name = "season_table", schema = "questv_schema")
public class SeasonModel implements Convertible<SeasonDTO>, Updatable<SeasonModel>, Questionable {


  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long seriesId;

  @NotNull
  private Integer number;

  @NotNull
  @NotEmpty
  private String name;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<EpisodeModel> episodes;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<QuestionModel> questions;

  /*default*/ SeasonModel() {
    this.episodes = new HashSet<>();
    this.questions = new HashSet<>();
  }

  /*default*/ SeasonModel(final Long id, final Long seriesId, final Integer number, final String name) {

    this();
    this.id = id;
    this.seriesId = seriesId;
    this.number = number;
    this.name = name;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public SeasonDTO convert() {
    final Set<Long> episodesIds = episodes
        .stream()
        .map(EpisodeModel::getId)
        .collect(Collectors.toSet());

    final Set<Long> questionsIds = this.questions
        .stream()
        .map(QuestionModel::getId)
        .collect(Collectors.toSet());

    return new SeasonDTO(getId(), getSeriesId(), getNumber(), getName(), episodesIds, questionsIds);
  }

  @Override
  public void update(final SeasonModel update) {
    setName(update.getName());
  }

  public Long getSeriesId() {
    return seriesId;
  }

  public void setSeriesId(Long seriesId) {
    this.seriesId = seriesId;
  }

  public Set<EpisodeModel> getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Set<EpisodeModel> episodes) {
    this.episodes = episodes;
  }

  public void attachEpisode(final EpisodeModel episodeModel) {
    this.episodes.add(episodeModel);
  }

  public void removeEpisodeById(final Long episodeId) {
    for (final EpisodeModel episodeModel : this.episodes) {
      if (episodeModel.getId().equals(episodeId)) {
        this.episodes.remove(episodeModel);
      }
    }
  }

  @Override
  public void attachQuestion(final QuestionModel questionModel) {
    this.questions.add(questionModel);
  }

  @Override
  public void attachAll(final Set<QuestionModel> questionModelSet) {
    this.questions.addAll(questionModelSet);
  }

  @Override
  public void detachQuestion(final QuestionModel questionModel) {
    this.questions.remove(questionModel);
  }

  @Override
  public Set<QuestionModel> getQuestions() {
    return this.questions;
  }

  @Override
  public Set<QuestionModel> getQuestionsRecursively() {
    final Set<QuestionModel> result = new HashSet<>(this.questions);
    for (final Questionable questionable : this.episodes) {
      result.addAll(questionable.getQuestionsRecursively());
    }
    return result;
  }
}
