package com.questv.series;

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
