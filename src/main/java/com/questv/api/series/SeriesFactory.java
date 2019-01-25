package com.questv.api.series;

import com.questv.api.episode.EpisodeModel;
import com.questv.api.season.SeasonModel;

public class SeriesFactory {

  public static SeriesModel createSerie(final String name) {
    return new SeriesModel(name, "");
  }

  public static SeasonModel createSeason(final String name) {
    return null;
  }

  public static EpisodeModel creataEpisode(final String name) {
    return null;
  }

}
