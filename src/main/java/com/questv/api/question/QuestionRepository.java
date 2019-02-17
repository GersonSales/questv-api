package com.questv.api.question;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<QuestionModel, Long> {
  List<QuestionModel> findAll();

}
