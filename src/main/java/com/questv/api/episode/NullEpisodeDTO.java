package com.questv.api.episode;

import java.util.HashSet;

public final class NullEpisodeDTO extends EpisodeDTO {

  /*default*/ NullEpisodeDTO() {
    super(0L, 0L, "", 0,  new HashSet<>());
  }
}
