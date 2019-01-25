package com.questv.api.season;

import com.questv.api.util.Convertible;
import com.questv.api.util.Updatable;

public class SeasonDTO implements Convertible<SeasonModel>, Updatable<SeasonDTO> {

  private Long id;
  private String name;


  /*default*/ SeasonDTO(final Long id, final String name) {
    this.id = id;
    this.name = name;

  }

  @Override
  public SeasonModel convert() {
    return null;
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
}
