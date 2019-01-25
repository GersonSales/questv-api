package com.questv.api.question;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<QuestionModel, Long> {
  List<QuestionModel> findAll();
  QuestionModel findById(final long id);

}
