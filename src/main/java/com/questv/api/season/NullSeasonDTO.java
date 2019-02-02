package com.questv.api.season;

import java.util.HashSet;

public final class NullSeasonDTO extends SeasonDTO {
  NullSeasonDTO() {
    super(0L, 0L, 0, "", new HashSet<>(), new HashSet<>());
  }
}
