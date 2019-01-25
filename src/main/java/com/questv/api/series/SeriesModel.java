package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.question.Question;
import com.questv.api.question.Questionable;
import com.questv.api.season.Season;
import com.questv.api.util.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "series_table", schema = "questv_schema")
public class SeriesModel implements Convertible<SeriesDTO>, Updatable<SeriesModel> {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  private String name;

  @Embedded
  private Set<Season> seasons;
//
//  private Set<Question> questionSet;


  /*default*/ SeriesModel() {
//    questionSet = new HashSet<>();
    seasons = new HashSet<>();

  }

  /*default*/ SeriesModel(final String name) {
    this();
    this.name = name;
  }

//  public void attachQuestion(final Question question) {
//    this.questionSet.add(question);
//  }

//  @Override
//  public void attachAll(final Set<Question> questionSet) {
//    this.questionSet.addAll(questionSet);
//  }


  @Override
  public SeriesDTO convert() {
    return new SeriesDTO(name,
        seasons
            .stream()
            .map(Season::getId)
            .collect(Collectors.toList()));
  }

  @Override
  public void update(final SeriesModel update) {

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

  public Set<Season> getSeasons() {
    return seasons;
  }

  public void setSeasons(Set<Season> seasons) {
    this.seasons = seasons;
  }

}
