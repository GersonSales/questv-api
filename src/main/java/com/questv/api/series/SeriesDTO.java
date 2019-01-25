package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.util.Updatable;

import java.util.List;

public class SeriesDTO implements Convertible<SeriesModel>, Updatable<SeriesDTO> {

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
    return null;
  }

  @Override
  public void update(SeriesDTO update) {

  }
}
