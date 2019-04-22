package com.questv.api.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Questionable;
import com.questv.api.contracts.Updatable;
import com.questv.api.question.difficult.Difficult;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Embeddable
@Entity
@Table(name = "question_table")
public final class QuestionModel implements Convertible<QuestionDTO>, Updatable<QuestionModel> {


  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String description;

  @NotNull
  @OneToOne(cascade = CascadeType.ALL)
  private Difficult difficult;

  @NotNull
  private Long questionableId;

  private Double rate;

  @NotNull
  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<Answer> answerSet;

  /*default*/ QuestionModel() {
  }

  /*default*/ QuestionModel(final Long id,
                            final String description,
                            final Difficult difficult,
                            final Long questionableId,
                            final Double rate,
                            final Set<Answer> answerSet) {
    this.id = id;
    this.description = description;
    this.difficult = difficult;
    this.questionableId = questionableId;
    this.rate = rate;
    this.answerSet = answerSet;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
  }

  public Set<Answer> getAnswerSet() {
    return answerSet;
  }

  public void setAnswerSet(Set<Answer> answerSet) {
    this.answerSet = answerSet;
  }


  public void setQuestionableId(Long questionableId) {
    this.questionableId = questionableId;
  }

  public Long getQuestionableId() {
    return questionableId;
  }

  public Difficult getDifficult() {
    return difficult;
  }

  public void setDifficult(final Difficult difficult) {
    this.difficult = difficult;
  }

  @Override
  public QuestionDTO convert() {
    final Map<Long, Map<String, Boolean>> answerIdsMap = new HashMap<>();
    for (final Answer answer : this.answerSet) {
      final Map<String, Boolean> answersMap = new HashMap<>();
      answersMap.put(answer.getValue(), answer.isCorrect());
      answerIdsMap.put(answer.getId(), answersMap);
    }

    final Integer difficult = getDifficult().getDifficult();
    final Integer reward = getDifficult().getReward() +  (int)(0.2 * rate);

    return new QuestionDTO(getId(),
        getDescription(),
        difficult,
        reward,
        getQuestionableId(),
        getRate(),
        answerIdsMap);
  }

  @Override
  public void update(final QuestionModel update) {
    setDescription(update.getDescription());
    setAnswerSet(update.getAnswerSet());
    setRate((getRate() + update.getRate()) / 2.0);
    difficult.evaluate(update.getDifficult().getDifficult());
  }
}
