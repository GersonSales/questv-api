package com.questv.api.episode;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Questionable;
import com.questv.api.contracts.Updatable;
import com.questv.api.question.QuestionModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "episodes")
public class EpisodeModel implements Convertible<EpisodeDTO>,
    Updatable<EpisodeModel>, Questionable {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Column(name = "FK_season")
  private Long seasonId;

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  private Integer number;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "FK_questionable")
  private final Set<QuestionModel> questions;

  protected EpisodeModel() {
    this.questions = new HashSet<>();
  }

  /*default*/ EpisodeModel(final Long id,
                           final Long seasonId,
                           final String name,
                           final Integer number) {
    this();
    this.id = id;
    this.seasonId = seasonId;
    this.name = name;
    this.number = number;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(Long ownerId) {
    this.seasonId = ownerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @Override
  public EpisodeDTO convert() {
    final Set<Long> questionsIds = questions == null ? new HashSet<>() :
        questions//TODO
        .stream()
        .map(QuestionModel::getId)
        .collect(Collectors.toSet());

    return new EpisodeDTO(getId(), getSeasonId(), getName(), getNumber(),
        questionsIds);
  }

  @Override
  public void update(final EpisodeModel update) {
    setName(update.getName());
    setNumber(update.getNumber());
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
    return getQuestions();
  }

  @Override
  public Integer getQuestionsCount() {
    return getQuestions().size();
  }
}
