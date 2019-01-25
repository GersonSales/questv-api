package com.questv.api.series;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Questionable;
import com.questv.api.question.QuestionModel;
import com.questv.api.season.SeasonModel;
import com.questv.api.contracts.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "series_table", schema = "questv_schema")
public class SeriesModel implements Convertible<SeriesDTO>, Updatable<SeriesModel>, Questionable {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String abbreviation;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<SeasonModel> seasons;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<QuestionModel> questions;


  /*default*/ SeriesModel() {
    this.seasons = new HashSet<>();
    this.questions = new HashSet<>();
  }

  /*default*/ SeriesModel(final String name, final String abbreviation) {
    this();
    this.name = name;
    this.abbreviation = abbreviation;
  }

  @Override
  public SeriesDTO convert() {
    final Set<Long> seasonsIds = this.seasons
        .stream()
        .map(SeasonModel::getId)
        .collect(Collectors.toSet());

    final Set<Long> questionsIds = this.questions
        .stream()
        .map(QuestionModel::getId)
        .collect(Collectors.toSet());

    return new SeriesDTO(getId(), getName(), getAbbreviation(), seasonsIds, questionsIds);
  }

  @Override
  public void update(final SeriesModel update) {
    this.name = update.name;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<SeasonModel> getSeasons() {
    return seasons;
  }

  public void setSeasons(Set<SeasonModel> seasons) {
    this.seasons = seasons;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public void attachSeason(final SeasonModel seasonModel) {
    this.seasons.add(seasonModel);
  }

  public void removeSeasonById(final Long seasonId) {
    for (final SeasonModel seasonModel : this.seasons) {
      if (seasonModel.getId().equals(seasonId)) {
        this.seasons.remove(seasonModel);
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
}
