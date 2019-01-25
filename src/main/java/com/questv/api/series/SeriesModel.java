package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.season.Season;
import com.questv.api.util.Updatable;

import javax.persistence.*;
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

  @NotNull
  private String abbreviation;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<Season> seasons;


  /*default*/ SeriesModel() {
    this.seasons = new HashSet<>();
  }

  /*default*/ SeriesModel(final String name, final String abbreviation) {
    this();
    this.name = name;
    this.abbreviation = abbreviation;
  }

  @Override
  public SeriesDTO convert() {
    Set<Long> collect = seasons
        .stream()
        .map(Season::getId)
        .collect(Collectors.toSet());

    return new SeriesDTO(getId(), getName(), getAbbreviation(), collect);
  }

  @Override
  public void update(final SeriesModel update) {
    this.name = update.name;

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

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }
}
