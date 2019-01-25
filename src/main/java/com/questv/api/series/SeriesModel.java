package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.season.SeasonModel;
import com.questv.api.util.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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
  private Set<SeasonModel> seasonModels;


  /*default*/ SeriesModel() {
    this.seasonModels = new HashSet<>();
  }

  /*default*/ SeriesModel(final String name, final String abbreviation) {
    this();
    this.name = name;
    this.abbreviation = abbreviation;
  }

  @Override
  public SeriesDTO convert() {
    Set<Long> collect = seasonModels
        .stream()
        .map(SeasonModel::getId)
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

  public Set<SeasonModel> getSeasonModels() {
    return seasonModels;
  }

  public void setSeasonModels(Set<SeasonModel> seasonModels) {
    this.seasonModels = seasonModels;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public void attachSeason(final SeasonModel seasonModel) {
    this.seasonModels.add(seasonModel);
  }

  public void removeSeasonById(final Long seasonId) {
    for (final SeasonModel seasonModel : this.seasonModels) {
      if (seasonModel.getId().equals(seasonId)) {
        this.seasonModels.remove(seasonModel);
      }
    }
  }
}
