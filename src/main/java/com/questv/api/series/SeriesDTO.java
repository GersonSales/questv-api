package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.util.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class SeriesDTO implements Convertible<SeriesModel>, Updatable<SeriesDTO> {

  private Long id;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series name should have 3 characters at least.")
  private String name;

  private Set<Long> seasonsIds;


  /*default*/ SeriesDTO() {
    this.seasonsIds = new HashSet<>();
  }

  /*default*/ SeriesDTO(final String name) {
    this();
    this.name = name;
  }


  @Override
  public SeriesModel convert() {
    return new SeriesModel(getName());
  }

  @Override
  public void update(final SeriesDTO update) {
    this.name = update.name;

  }

  public String getName() {
    return name;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Long> getSeasonsIds() {
    return seasonsIds;
  }
}
