package com.questv.api.season;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class SeasonDTO implements Convertible<SeasonModel>, Updatable<SeasonDTO> {

  private String id;

  @NotNull
  private Integer number;

  @NotNull
  private String seriesId;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Season name should have 3 characters at least.")
  private String name;

  private Set<String> episodes;

  private Set<String> questions;

  /*default*/ SeasonDTO() { }

  /*default*/ SeasonDTO(final String id,
                        final String seriesId,
                        final Integer number,
                        final String name,
                        final Set<String> episodes,
                        final Set<String> questions) {
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public String getSeriesId() {
    return seriesId;
  }

  public void setSeriesId(String seriesId) {
    this.seriesId = seriesId;
  }

  public Set<String> getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Set<String> episodes) {
    this.episodes = episodes;
  }

  public Set<String> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<String> questions) {
    this.questions = questions;
  }
}
