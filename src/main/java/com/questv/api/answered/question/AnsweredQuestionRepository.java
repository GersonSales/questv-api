package com.questv.api.answered.question;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnsweredQuestionRepository extends CrudRepository<AnsweredQuestionModel, Long> {

    @Override
    List<AnsweredQuestionModel> findAll();
}
