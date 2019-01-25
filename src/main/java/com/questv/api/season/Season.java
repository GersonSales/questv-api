package com.questv.api.season;

import com.questv.api.episode.Episode;
import com.questv.api.question.Question;
import com.questv.api.question.Questionable;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Table(name = "season_table", schema = "questv_schema")
public class Season {


  private  Long id;
  private String name;


  /*default*/  Season() { }

  public Season(final String name) {
    this.name = name;
    id = 1L;
  }

  public Long getId() {
    return null;
  }
}
