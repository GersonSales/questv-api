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
  private String id;

  @NotNull
  private String questionId;

  @NotNull
  private String answerId;

  public AnsweredQuestionModel() {
  }

  public AnsweredQuestionModel(final String questionId, final String answerId) {
    this.questionId = questionId;
    this.answerId = answerId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  public String getAnswerId() {
    return answerId;
  }

  public void setAnswerId(String answerId) {
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
