package com.questv.api.answered.question;

import com.questv.api.question.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "answeredQuestionService")
public class AnsweredQuestionService {

  private final AnsweredQuestionRepository answeredQuestionRepository;
  private final QuestionService questionService;

  public AnsweredQuestionService(final AnsweredQuestionRepository answeredQuestionRepository,
                                 final QuestionService questionService) {
    this.answeredQuestionRepository = answeredQuestionRepository;
    this.questionService = questionService;
    assert this.answeredQuestionRepository != null;
    assert this.questionService!= null;
  }

  public AnsweredQuestionModel create(final AnsweredQuestionModel answeredQuestionModel) {
    return this.answeredQuestionRepository.save(answeredQuestionModel);
  }


  public AnsweredQuestionModel find(final AnsweredQuestionModel answeredQuestionModel) {
    this.questionService.findById(answeredQuestionModel.getQuestionId());

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
