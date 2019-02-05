package com.questv.api.series;

import java.util.HashSet;

public final class NullSeriesModelable extends SeriesModelable {
  public NullSeriesModelable() {
    super(0L, "NotASeries", "NaS", "", false, "", "", "", "", new HashSet<>(), new HashSet<>());

  }
}
