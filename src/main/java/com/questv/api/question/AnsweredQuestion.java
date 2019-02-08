package com.questv.api.question;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Embeddable
public class AnsweredQuestion {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long questionId;

  @NotNull
  private Long answerId;

  public AnsweredQuestion() {
  }

  public AnsweredQuestion(final Long questionId, final Long answerId) {
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
    if (!(o instanceof AnsweredQuestion)) return false;
    AnsweredQuestion that = (AnsweredQuestion) o;
    return Objects.equals(getId(), that.getId()) &&
        Objects.equals(getQuestionId(), that.getQuestionId()) &&
        Objects.equals(getAnswerId(), that.getAnswerId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getQuestionId(), getAnswerId());
  }
}
