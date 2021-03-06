package com.questv.api.episode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.questv.api.contracts.Convertible;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class EpisodeDTO implements Convertible<EpisodeModel> {


  private Long id;

  @JsonIgnore
  private Long ownerId;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Episode name should have 3 characters at least.")
  private String name;

  @NotNull
  private Integer number;

  private Set<Long> questions;

  /*default*/ EpisodeDTO() {
    this.questions = new HashSet<>();
  }

  /*default*/ EpisodeDTO(final Long id,
                         final Long ownerId,
                         final String name,
                         final Integer number,
                         final Set<Long> questions) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.number = number;
    this.questions = questions;

  }

  @Override
  public EpisodeModel convert() {
    return new EpisodeModel(getId(), getOwnerId(), getName(), getNumber());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
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

  public Set<Long> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Long> questions) {
    this.questions = questions;
  }
}
