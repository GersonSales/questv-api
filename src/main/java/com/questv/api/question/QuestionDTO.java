package com.questv.api.question;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;
import com.questv.api.question.difficult.Difficult;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuestionDTO implements Convertible<QuestionModel>, Updatable<QuestionDTO> {

  private Long id;

  @NotNull
  private Long ownerId;

  @NotNull
  private String description;

  @NotNull
  private Integer difficult;

  @NotNull
  private Integer reward;

  @NotNull
  private Map<String, Boolean> answers;


  /*default*/ QuestionDTO() {
  }

  /*default*/ QuestionDTO(final Long id,
                          final Long ownerId,
                          final String description,
                          final Integer difficult,
                          final Integer reward,
                          final Map<String, Boolean> answers) {

    this.id = id;
    this.ownerId = ownerId;
    this.description = description;
    this.difficult = difficult;
    this.reward = reward;
    this.answers = answers;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Map<String, Boolean> getAnswers() {
    return answers;
  }

  public void setAnswers(Map<String, Boolean> answers) {
    this.answers = answers;
  }

  public Integer getDifficult() {
    return difficult;
  }

  public void setDifficult(final Integer difficult) {
    this.difficult = difficult;
  }

  public Integer getReward() {
    return reward;
  }

  public void setReward(Integer reward) {
    this.reward = reward;
  }

  @Override
  public QuestionModel convert() {
    final Set<Answer> answerSet = new HashSet<>();
    for (final String answer : this.answers.keySet()) {
      answerSet.add(new Answer(answer, this.answers.get(answer)));
    }


    final Difficult difficult =  new Difficult(getDifficult());
    return new QuestionModel(getId(), getOwnerId(), getDescription(), difficult, answerSet);
  }

  @Override
  public void update(final QuestionDTO update) {
    setDescription(update.getDescription());
    setAnswers(update.getAnswers());
    setOwnerId(update.getOwnerId());
    setDifficult(update.getDifficult());
  }
}
