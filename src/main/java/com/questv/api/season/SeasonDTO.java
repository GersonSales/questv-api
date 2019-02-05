package com.questv.api.season;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class SeasonDTO implements Convertible<SeasonModel>, Updatable<SeasonDTO> {

  private Long id;

  @NotNull
  private Integer number;

  @NotNull
  private Long seriesId;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Season name should have 3 characters at least.")
  private String name;

  private Set<Long> episodes;

  private Set<Long> questions;

  /*default*/ SeasonDTO() { }

  /*default*/ SeasonDTO(final Long id,
                        final Long seriesId,
                        final Integer number,
                        final String name,
                        final Set<Long> episodes,
                        final Set<Long> questions) {
    this.id = id;
    this.seriesId = seriesId;
    this.number = number;
    this.name = name;
    this.episodes = episodes;
    this.questions = questions;

  }

  @Override
  public SeasonModel convert() {
    return new SeasonModel(getId(), getSeriesId(), getNumber(), getName());
  }

  @Override
  public void update(final SeasonDTO update) {
    setName(update.getName());
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

  public Long getSeriesId() {
    return seriesId;
  }

  public void setSeriesId(Long seriesId) {
    this.seriesId = seriesId;
  }

  public Set<Long> getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Set<Long> episodes) {
    this.episodes = episodes;
  }

  public Set<Long> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Long> questions) {
    this.questions = questions;
  }
}
