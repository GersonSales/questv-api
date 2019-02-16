package com.questv.api.episode;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends CrudRepository<EpisodeModel, Long> {
  List<EpisodeModel> findByName(final String name);

  Optional<EpisodeModel> findById(final String id);

  void deleteById(final String id);
}
