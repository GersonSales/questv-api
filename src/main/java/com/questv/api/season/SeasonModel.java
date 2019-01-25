package com.questv.api.season;

import com.questv.api.util.Convertible;
import com.questv.api.util.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Embeddable
@Table(name = "season_table", schema = "questv_schema")
public class SeasonModel implements Convertible<SeasonDTO>, Updatable<SeasonModel> {


  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private  Long id;

  @NotNull
  private String name;


  /*default*/ SeasonModel() {
  }

  /*default*/ SeasonModel(final String name) {
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
    return new SeasonDTO(getId(), getName());
  }

  @Override
  public void update(final SeasonModel update) {
    setName(update.getName());
  }
}
