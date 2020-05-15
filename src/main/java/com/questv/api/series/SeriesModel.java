package com.questv.api.series;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Modelable;
import com.questv.api.contracts.Questionable;
import com.questv.api.exception.SeasonNotFoundException;
import com.questv.api.question.QuestionModel;
import com.questv.api.rate.RateModel;
import com.questv.api.season.SeasonModel;
import com.questv.api.contracts.Updatable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "series")
public class SeriesModel implements Convertible<SeriesDTO>,
    Updatable<SeriesModel>, Questionable, Modelable {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String abbreviation;

  @NotNull
  private String category;

  @NotNull
  @Column(name = "release")
  private Boolean isRelease;

  private Double rate;


//  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//  private RateModel rateModel;

  private String coverImage;
  private String coverImageUrl;

  private String promoImage;
  private String promoImageUrl;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "FK_series")
  private final Set<SeasonModel> seasons;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "FK_questionable")
  private final Set<QuestionModel> questions;


  protected SeriesModel() {
    setName("");
    setAbbreviation("");
    setCategory("");
    setIsRelease(false);
    setRate(0.0);
    setCoverImage("");
    setCoverImageUrl("");
    setPromoImage("");
    setPromoImageUrl("");
    this.seasons = new HashSet<>();
    this.questions = new HashSet<>();
  }

  /*default*/ SeriesModel(final Long id,
                          final String name,
                          final String abbreviation,
                          final String category,
                          final Boolean isRelease,
                          final Double rate,
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
    this.rate = rate;
    this.coverImage = coverImage;
    this.coverImageUrl = coverImageUrl;
    this.promoImage = promoImage;
    this.promoImageUrl = promoImageUrl;
  }

  @Override
  public SeriesDTO convert() {
    final Set<Long> seasonsIds = this.seasons
        .stream()
        .map(SeasonModel::getId)
        .collect(Collectors.toSet());

    final Set<Long> questionsIds = this.questions
        .stream()
        .map(QuestionModel::getId)
        .collect(Collectors.toSet());

    return new SeriesDTO(getId(),
        getName(),
        getAbbreviation(),
        getCategory(),
        getIsRelease(),
        getRate(),
        getCoverImage(),
        getCoverImageUrl(),
        getPromoImage(),
        getPromoImageUrl(),
        seasonsIds,
        questionsIds);
  }

  @Override
  public void update(final SeriesModel update) {
    setName(update.getName());
    setAbbreviation(update.getAbbreviation());
    setCategory(update.getCategory());
    setIsRelease(update.getIsRelease());
    setRate((getRate() + update.getRate()) / 2.0);
    setCoverImage(update.getCoverImage());
    setCoverImageUrl(update.getCoverImageUrl());
    setPromoImage(update.getPromoImage());
    setPromoImageUrl(update.getPromoImageUrl());
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

  public Set<SeasonModel> getSeasons() {
    return seasons;
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

  public Double getRate() {
    return rate == null ? 0.0 : rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
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

  public void removeSeasonById(final Long seasonId) {
    this.seasons.removeIf(seasonModel -> seasonModel.getId().equals(seasonId));
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

  public SeasonModel getSeasonByNumber(final Integer seasonNumber) {
    for (final SeasonModel seasonModel : this.seasons) {
      if (seasonModel.getNumber().equals(seasonNumber)) {
        return seasonModel;
      }
    }

    throw new SeasonNotFoundException();
  }

  @Override
  public Integer getQuestionsCount() {
    return getQuestionsRecursively().size();
  }
}
