package com.questv.api.episode;

import com.questv.api.contracts.Convertible;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EpisodeDTO implements Convertible<EpisodeModel> {


  private Long id;

  @NotNull
  private Long seasonId;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Episode name should have 3 characters at least.")
  private String name;

  /*default*/ EpisodeDTO() { }

  /*default*/ EpisodeDTO(final Long id, final Long seasonId, final String name) {
    this.id = id;
    this.seasonId = seasonId;
    this.name = name;
  }

  @Override
  public EpisodeModel convert() {
    return new EpisodeModel(getSeasonId(), getName());
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

  public void setSeasonId(Long seasonId) {
    this.seasonId = seasonId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
