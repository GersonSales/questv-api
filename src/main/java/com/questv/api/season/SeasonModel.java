package com.questv.api.season;

import com.questv.api.episode.EpisodeModel;
import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Embeddable
@Table(name = "season_table", schema = "questv_schema")
public class SeasonModel implements Convertible<SeasonDTO>, Updatable<SeasonModel> {


  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long seriesId;

  @NotNull
  private String name;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<EpisodeModel> episodeModels;

  /*default*/ SeasonModel() {
    this.episodeModels = new HashSet<>();
  }

  /*default*/ SeasonModel(final Long seriesId, final String name) {
    this();
    this.seriesId = seriesId;
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

  @Override
  public SeasonDTO convert() {
    return new SeasonDTO(getId(), getSeriesId(), getName());
  }

  @Override
  public void update(final SeasonModel update) {
    setName(update.getName());
  }

  public Long getSeriesId() {
    return seriesId;
  }

  public void setSeriesId(Long seriesId) {
    this.seriesId = seriesId;
  }

  public Set<EpisodeModel> getEpisodeModels() {
    return episodeModels;
  }

  public void setEpisodeModels(Set<EpisodeModel> episodeModels) {
    this.episodeModels = episodeModels;
  }

  public void attachEpisode(final EpisodeModel episodeModel) {
    this.episodeModels.add(episodeModel);
  }

  public void removeEpisodeById(final Long episodeId) {
    for(final EpisodeModel episodeModel : this.episodeModels) {
      if(episodeModel.getId().equals(episodeId)) {
        this.episodeModels.remove(episodeModel);
      }
    }
  }
}
