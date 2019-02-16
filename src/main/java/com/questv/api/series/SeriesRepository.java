package com.questv.api.series;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SeriesRepository extends CrudRepository<SeriesModel, Long> {
  Optional<SeriesModel> findById(final String id);
  void deleteById(final String id);


}
