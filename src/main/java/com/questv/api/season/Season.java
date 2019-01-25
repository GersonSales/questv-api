package com.questv.api.season;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Embeddable
@Table(name = "season_table", schema = "questv_schema")
public class Season {


  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private  Long id;

  @NotNull
  private String name;


  /*default*/  Season() {
    this.id = 1L;
  }

  public Season(final String name) {
    this.id = 1L;
    this.name = name;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
