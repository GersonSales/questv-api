package com.questv.api.season;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SeasonDTO implements Convertible<SeasonModel>, Updatable<SeasonDTO> {

  private Long id;

  @NotNull
  private Long seriesId;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Season name should have 3 characters at least.")
  private String name;

  /*default*/ SeasonDTO() { }

  /*default*/ SeasonDTO(final Long id, final Long seriesId, final String name) {
    this.id = id;
    this.seriesId = seriesId;
    this.name = name;

  }

  @Override
  public SeasonModel convert() {
    return new SeasonModel(getSeriesId(), getName());
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
}
