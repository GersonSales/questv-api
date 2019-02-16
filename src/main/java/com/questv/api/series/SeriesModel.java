package com.questv.api.series;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Modelable;
import com.questv.api.contracts.Questionable;
import com.questv.api.question.QuestionModel;
import com.questv.api.season.SeasonModel;
import com.questv.api.contracts.Updatable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "series_table", schema = "questv_schema")
public class SeriesModel implements Convertible<SeriesDTO>, Updatable<SeriesModel>, Questionable, Modelable {

  @Id
  @NotNull
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String id;

  @NotNull
  private String name;

  @NotNull
  private String abbreviation;

  @NotNull
  private String category;

  @NotNull
  private Boolean isRelease;

  private String coverImage;
  private String coverImageUrl;

  private String promoImage;
  private String promoImageUrl;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<SeasonModel> seasons;

  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<QuestionModel> questions;


  /*default*/ SeriesModel() {
    setName("");
    setAbbreviation("");
    setCategory("");
    setIsRelease(false);
    setCoverImage("");
    setCoverImageUrl("");
    setPromoImage("");
    setPromoImageUrl("");
    setSeasons(new HashSet<>());
    setQuestions(new HashSet<>());
  }

  /*default*/ SeriesModel(final String id,
                          final String name,
                          final String abbreviation,
                          final String category,
                          final Boolean isRelease,
                          final String coverImage,
                          final String coverImageUrl,
                          final String promoImage,
                          final String promoImageUrl) {
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
  }

  @Override
  public SeriesDTO convert() {
    final Set<String> seasonsIds = this.seasons
        .stream()
        .map(SeasonModel::getId)
        .collect(Collectors.toSet());

    final Set<String> questionsIds = this.questions
        .stream()
        .map(QuestionModel::getId)
        .collect(Collectors.toSet());

    return new SeriesDTO(getId(), getName(), getAbbreviation(), getCategory(), getIsRelease(), getCoverImage(), getCoverImageUrl(), getPromoImage(), getPromoImageUrl(), seasonsIds, questionsIds);
  }

  @Override
  public void update(final SeriesModel update) {
    setName(update.getName());
    setAbbreviation(update.getAbbreviation());
    setCategory(update.getCategory());
    setIsRelease(update.getIsRelease());
    setCoverImage(update.getCoverImage());
    setCoverImageUrl(update.getCoverImageUrl());
    setPromoImage(update.getPromoImage());
    setPromoImageUrl(update.getPromoImageUrl());
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

  public Set<SeasonModel> getSeasons() {
    return seasons;
  }

  public void setSeasons(Set<SeasonModel> seasons) {
    this.seasons = seasons;
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

  public void setCoverImage(String coverImage) {
    this.coverImage = coverImage;
  }

  public String getPromoImage() {
    return promoImage;
  }

  public void setPromoImage(String promoImage) {
    this.promoImage = promoImage;
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

  public String getCoverImageUrl() {
    return coverImageUrl;
  }

  public void setCoverImageUrl(String coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
  }

  public void attachSeason(final SeasonModel seasonModel) {
    this.seasons.add(seasonModel);
  }

  public void removeSeasonById(final String seasonId) {
    for (final SeasonModel seasonModel : this.seasons) {
      if (seasonModel.getId().equals(seasonId)) {
        this.seasons.remove(seasonModel);
      }
    }
  }

  public void setQuestions(Set<QuestionModel> questions) {
    this.questions = questions;
  }

  @Override
  public void attachQuestion(final QuestionModel questionModel) {
    this.questions.add(questionModel);
  }

  @Override
  public void attachAll(final Set<QuestionModel> questionModelSet) {
    this.questions.addAll(questionModelSet);
  }

  @Override
  public void detachQuestion(final QuestionModel questionModel) {
    this.questions.remove(questionModel);
  }

  @Override
  public Set<QuestionModel> getQuestions() {
    return this.questions;
  }

  public String getPromoImageUrl() {
    return promoImageUrl;
  }

  public void setPromoImageUrl(String promoImageUrl) {
    this.promoImageUrl = promoImageUrl;
  }

  @Override
  public Set<QuestionModel> getQuestionsRecursively() {
    final Set<QuestionModel> result = new HashSet<>(this.questions);
    for (final Questionable questionable : this.seasons) {
      result.addAll(questionable.getQuestionsRecursively());
    }
    return result;
  }
}
