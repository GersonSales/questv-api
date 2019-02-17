package com.questv.api.question;


import org.hibernate.annotations.GenericGenerator;

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
  private String value;

  @NotNull
  private boolean isCorrect;

  /*default*/ Answer() { }


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

  public Long getId() {
    return id;
  }
}
