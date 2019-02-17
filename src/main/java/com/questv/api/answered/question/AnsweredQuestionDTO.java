package com.questv.api.answered.question;

import com.questv.api.contracts.Convertible;

import javax.validation.constraints.NotNull;

public class AnsweredQuestionDTO implements Convertible<AnsweredQuestionModel> {
  private Long id;

  @NotNull
  private Long questionId;

  @NotNull
  private Long answerId;

  /*default*/ AnsweredQuestionDTO() { }

  /*default*/ AnsweredQuestionDTO(final Long questionId, final Long answerId) {
    this.questionId = questionId;
    this.answerId = answerId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public Long getAnswerId() {
    return answerId;
  }

  public void setAnswerId(Long answerId) {
    this.answerId = answerId;
  }

  @Override
  public AnsweredQuestionModel convert() {
    return new AnsweredQuestionModel(getQuestionId(), getAnswerId());
  }
}
