package com.questv.api.series;

import java.util.List;

public class SeriesDTO {

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
}
