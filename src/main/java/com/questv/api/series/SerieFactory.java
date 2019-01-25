package com.questv.api.series;

import com.questv.api.episode.Episode;
import com.questv.api.season.Season;

public class SerieFactory {

  public static Series createSerie(final String name) {
    return new Series(name);
  }

  public static Season createSeason(final String name) {
    return new Season(name);
  }

  public static Episode creataEpisode(final String name) {
    return new Episode(name);
  }

}
