package com.questv.api.series;

import com.questv.api.util.Convertible;
import com.questv.api.question.Question;
import com.questv.api.question.Questionable;
import com.questv.api.season.Season;
import com.questv.api.util.Updatable;
import org.modelmapper.ModelMapper;

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

  /*default*/ SeriesModel() {
  }

  /*default*/ SeriesModel(final String name) {
    this();
    this.name = name;
  }

  @Override
  public SeriesDTO convert() {
    return new ModelMapper().map(this, SeriesDTO.class);

//    SeriesDTO seriesDTO = new SeriesDTO(getName());
//    seriesDTO.setId(getId());
//    return seriesDTO;
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

}
