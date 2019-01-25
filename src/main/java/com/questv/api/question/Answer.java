package com.questv.api.question;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Embeddable
@Table(name = "answer_table", schema = "questv_schema")
public class Answer {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private final String value;

  @NotNull
  private final boolean isCorrect;


  /*default*/ Answer(final String value, final boolean isCorrect) {
    this.value = value;
    this.isCorrect = isCorrect;
  }

  public String getValue() {
    return value;
  }

  public boolean isCorrect() {
    return isCorrect;
  }
}
