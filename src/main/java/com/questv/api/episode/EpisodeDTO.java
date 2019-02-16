package com.questv.api.episode;

import com.questv.api.contracts.Convertible;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class EpisodeDTO implements Convertible<EpisodeModel> {


  private String id;

  @NotNull
  private String seasonId;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Episode name should have 3 characters at least.")
  private String name;

  @NotNull
  private Integer number;

  private Set<String> questions;

  /*default*/ EpisodeDTO() {
    this.questions = new HashSet<>();
  }

  /*default*/ EpisodeDTO(final String id,
                         final String seasonId,
                         final String name,
                         final Integer number,
                         final Set<String> questions) {
    this.id = id;
    this.seasonId = seasonId;
    this.name = name;
    this.number = number;
    this.questions = questions;

  }

  @Override
  public EpisodeModel convert() {
    return new EpisodeModel(getId(), getSeasonId(), getName(), getNumber());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(String seasonId) {
    this.seasonId = seasonId;
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

  public Set<String> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<String> questions) {
    this.questions = questions;
  }
}
