package com.questv.api.season;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeasonRepository extends CrudRepository<SeasonModel, Long> {
  List<SeasonModel> findByName(final String name);
  SeasonModel findById(final long id);
}
