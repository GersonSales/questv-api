package com.questv.api.series;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Modelable;
import com.questv.api.contracts.Updatable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class SeriesDTO implements Convertible<SeriesModel>, Updatable<SeriesDTO>, Modelable {

  private String id;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series name should have 3 characters at least.")
  private String name;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series abbreviation should have 3 characters at least.")
  private String abbreviation;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Series category name should have 3 characters at least.")
  private String category;

  @NotNull
  private Boolean isRelease;

  private String coverImage;
  private  String coverImageUrl;

  private String promoImage;
  private String promoImageUrl;

  private Set<String> seasons;
  private Set<String> questions;

  /*default*/ SeriesDTO() {
    setName("");
    setCategory("");
    setIsRelease(false);
    setAbbreviation("");
    setCoverImage("");
    setCoverImageUrl("");
    setPromoImage("");
    setPromoImageUrl("");
    setSeasons(new HashSet<>());
    setQuestions(new HashSet<>());
  }

  /*default*/ SeriesDTO(final String id,
                        final String name,
                        final String abbreviation,
                        final String category,
                        final Boolean isRelease,
                        final String coverImage,
                        final String coverImageUrl,
                        final String promoImage,
                        final String promoImageUrl,
                        final Set<String> seasons,
                        final Set<String> questions) {
    this();
    this.id = id;
    this.name = name;
    this.abbreviation = abbreviation;
    this.category = category;
    this.isRelease = isRelease;
    this.coverImage = coverImage;
    this.coverImageUrl = coverImageUrl;
    this.promoImage = promoImage;
    this.promoImageUrl = promoImageUrl;
    this.seasons = seasons;
    this.questions = questions;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Boolean getIsRelease() {
    return isRelease;
  }

  public void setIsRelease(Boolean isRelease) {
    this.isRelease = isRelease;
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

  public String getPromoImage() {
    return promoImage;
  }

  public void setPromoImage(String promoImage) {
    this.promoImage = promoImage;
  }

  public Set<String> getSeasons() {
    return seasons;
  }

  public void setSeasons(Set<String> seasons) {
    this.seasons = seasons;
  }

  public String getCoverImageUrl() {
    return coverImageUrl;
  }

  public void setCoverImageUrl(String coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
  }

  public Set<String> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<String> questions) {
    this.questions = questions;
  }

  @Override
  public SeriesModel convert() {
    return new SeriesModel(
        getId(),
        getName(),
        getAbbreviation(),
        getCategory(),
        getIsRelease(),
        getCoverImage(),
        getCoverImageUrl(),
        getPromoImage(),
        getPromoImageUrl());
  }

  @Override
  public void update(final SeriesDTO update) {
    setName(update.getName());
    setCategory(update.getCategory());
    setIsRelease(update.getIsRelease());
    setAbbreviation(update.getAbbreviation());
    setCoverImage(update.getCoverImage());
    setCoverImageUrl(update.getCoverImageUrl());
    setPromoImage(update.getPromoImage());
    setPromoImageUrl(update.getPromoImageUrl());

  }

  public String getPromoImageUrl() {
    return promoImageUrl;
  }

  public void setPromoImageUrl(String promoImageUrl) {
    this.promoImageUrl = promoImageUrl;
  }
}
