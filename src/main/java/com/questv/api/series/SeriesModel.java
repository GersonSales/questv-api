package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.question.Question;
import com.questv.api.question.Questionable;
import com.questv.api.season.Season;
import com.questv.api.util.Updatable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeriesModel implements Questionable, Convertible<SeriesDTO>, Updatable<SeriesModel> {

  private Long id;
  private String name;
  private Set<Season> seasons;

  private Set<Question> questionSet;


  /*default*/ SeriesModel() {
    questionSet = new HashSet<>();
    seasons = new HashSet<>();

  }

  /*default*/ SeriesModel(final String name) {
    this();
    this.name = name;
  }

  public void attachQuestion(final Question question) {
    this.questionSet.add(question);
  }

  public Set<Question> getQuestions() {
    return this.questionSet;
  }

  @Override
  public void attachAll(final Set<Question> questionSet) {
    this.questionSet.addAll(questionSet);
  }

  public String getName() {
    return name;
  }

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
}
