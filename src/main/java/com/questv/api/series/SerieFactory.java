package com.questv.api.series;

import com.questv.api.episode.Episode;
import com.questv.api.season.Season;

public class SerieFactory {

  public static SeriesModel createSerie(final String name) {
    return new SeriesModel(name, "");
  }

  public static Season createSeason(final String name) {
    return null;
  }

  public static Episode creataEpisode(final String name) {
    return null;
  }

}
