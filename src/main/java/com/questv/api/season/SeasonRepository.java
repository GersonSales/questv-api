package com.questv.api.season;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository extends CrudRepository<SeasonModel, Long> {
  List<SeasonModel> findByName(final String name);
}
