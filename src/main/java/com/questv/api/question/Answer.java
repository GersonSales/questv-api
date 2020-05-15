package com.questv.api.question;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answers")
public class Answer {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String value;

  @NotNull
  @Column(name = "question_id")
  private Long questionId;

  @NotNull
  private boolean isCorrect;

  protected Answer() { }


  /*default*/ Answer(final String value, final boolean isCorrect) {
    this.value = value;
    this.isCorrect = isCorrect;
  }

  public String getValue() {
    return value;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public boolean isCorrect() {
    return isCorrect;
  }

  public Long getId() {
    return id;
  }
}
