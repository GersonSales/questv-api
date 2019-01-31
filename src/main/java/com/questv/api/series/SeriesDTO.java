package com.questv.api.series;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class SeriesDTO implements Convertible<SeriesModel>, Updatable<SeriesDTO> {

  private Long id;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series name should have 3 characters at least.")
  private String name;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series abbreviation should have 3 characters at least.")
  private String abbreviation;

  private String coverImage;

  private Set<Long> seasons;
  private Set<Long> questions;

  /*default*/ SeriesDTO() {
  }

  /*default*/ SeriesDTO(final Long id,
                        final String name,
                        final String abbreviation,
                        final String coverImage,
                        final Set<Long> seasons,
                        final Set<Long> questions) {
    this();
    this.id = id;
    this.name = name;
    this.abbreviation = abbreviation;
    this.coverImage = coverImage;
    this.seasons = seasons;
    this.questions = questions;
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

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getCoverImage() {
    return coverImage;
  }

  public void setCoverImage(final String coverImage) {
    this.coverImage = coverImage;
  }

  public Set<Long> getSeasons() {
    return seasons;
  }

  public void setSeasons(Set<Long> seasons) {
    this.seasons = seasons;
  }

  public Set<Long> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Long> questions) {
    this.questions = questions;
  }

  @Override
  public SeriesModel convert() {
    return new SeriesModel(getName(), getAbbreviation(), getCoverImage());
  }

  @Override
  public void update(final SeriesDTO update) {
    setName(update.getName());
    setAbbreviation(update.getAbbreviation());
    setCoverImage(update.getCoverImage());

  }
}
