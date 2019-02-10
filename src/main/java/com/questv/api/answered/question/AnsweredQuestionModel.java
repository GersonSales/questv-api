package com.questv.api.answered.question;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "answered_question_table")
public class AnsweredQuestionModel {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long questionId;

  @NotNull
  private Long answerId;

  public AnsweredQuestionModel() {
  }

  public AnsweredQuestionModel(final Long questionId, final Long answerId) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AnsweredQuestionModel)) return false;
    AnsweredQuestionModel that = (AnsweredQuestionModel) o;
    return getQuestionId().equals(that.getQuestionId()) &&
        getAnswerId().equals(that.getAnswerId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getQuestionId(), getAnswerId());
  }
}
