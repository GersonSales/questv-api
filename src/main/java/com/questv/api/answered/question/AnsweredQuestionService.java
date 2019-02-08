package com.questv.api.answered.question;

import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "answeredQuestionService")
public class AnsweredQuestionService {

  private final AnsweredQuestionRepository answeredQuestionRepository;

  public AnsweredQuestionService(
      final AnsweredQuestionRepository answeredQuestionRepository) {
    this.answeredQuestionRepository = answeredQuestionRepository;
  }

  public AnsweredQuestionModel create(final AnsweredQuestionModel answeredQuestionModel) {
    return this.answeredQuestionRepository.save(answeredQuestionModel);
  }


  public AnsweredQuestionModel find(
      final AnsweredQuestionModel answeredQuestionModel) {
    final List<AnsweredQuestionModel> answeredQuestionModels
        = this.answeredQuestionRepository.findAll();

    for (final AnsweredQuestionModel answeredQuestion : answeredQuestionModels) {
      if (answeredQuestion.equals(answeredQuestionModel)) {
        return answeredQuestion;
      }
    }
    return null;
  }

}
