package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.util.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SeriesDTO implements Convertible<SeriesModel>, Updatable<SeriesDTO> {

  private Long id;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series name should have 3 characters at least.")
  private final String name;

  private final List<Long> seasonsIds;

  /*default*/ SeriesDTO(final String name, final List<Long> seasonsIds) {
    this.name = name;
    this.seasonsIds = seasonsIds;
  }


  @Override
  public String toString() {
    return "SeriesDTO{" +
        "name='" + name + '\'' +
        ", seasonsIds=" + seasonsIds +
        '}';
  }

  @Override
  public SeriesModel convert() {
    return new SeriesModel(getName());
  }

  @Override
  public void update(SeriesDTO update) {

  }

  public String getName() {
    return name;
  }
}
