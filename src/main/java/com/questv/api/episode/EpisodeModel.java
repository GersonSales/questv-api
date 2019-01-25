package com.questv.api.episode;

import com.questv.api.question.Question;
import com.questv.api.question.Questionable;
import com.questv.api.util.Convertible;
import com.questv.api.util.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Embeddable
@Table(name = "episode_table", schema = "questv_schema")
public class EpisodeModel implements Convertible<EpisodeDTO>, Updatable<EpisodeModel> {


  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long seasonId;

  @NotNull
  @NotEmpty
  private String name;

  /*default*/ EpisodeModel() { }

  /*default*/ EpisodeModel(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(Long seasonId) {
    this.seasonId = seasonId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public EpisodeDTO convert() {
    return new EpisodeDTO();
  }

  @Override
  public void update(final EpisodeModel update) {
    setName(update.getName());
  }
}
