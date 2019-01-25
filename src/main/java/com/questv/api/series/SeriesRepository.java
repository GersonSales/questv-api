package com.questv.api.series;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeriesRepository extends CrudRepository<SeriesModel, Long> {
  List<SeriesModel> findByName(final String name);

  SeriesModel findByEmail(final String email);

  SeriesModel findById(final long id);
}
