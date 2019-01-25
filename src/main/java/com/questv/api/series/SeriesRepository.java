package com.questv.api.series;

import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<SeriesModel, Long> {
  SeriesModel findById(final long id);


}
