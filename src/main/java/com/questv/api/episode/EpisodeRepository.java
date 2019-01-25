package com.questv.api.episode;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpisodeRepository extends CrudRepository<EpisodeModel, Long> {
  List<EpisodeModel> findByName(final String name);
  EpisodeModel findById(final long id);
}
