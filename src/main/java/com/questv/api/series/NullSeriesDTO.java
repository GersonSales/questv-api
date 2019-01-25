package com.questv.api.series;

import java.util.HashSet;

public final class NullSeriesDTO extends SeriesDTO {
  public NullSeriesDTO() {
    super(0L, "NotASeries", "NaS", new HashSet<>());

  }
}
