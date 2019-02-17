package com.questv.api.question;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;
import com.questv.api.question.difficult.Difficult;

import javax.validation.constraints.NotNull;
import java.util.*;

public class QuestionDTO implements Convertible<QuestionModel>, Updatable<QuestionDTO> {

  private Long id;

  @NotNull
  private String description;

  @NotNull
  private Integer difficult;

  @NotNull
  private Integer reward;

  @NotNull
  private Map<Long, Map<String, Boolean>> _answers;


  /*default*/ QuestionDTO() {
    this._answers = new HashMap<>();
  }

  /*default*/ QuestionDTO(final Long id,
                          final String description,
                          final Integer difficult,
                          final Integer reward,
                          final Map<Long, Map<String, Boolean>> _answers) {

    this.id = id;
    this.description = description;
    this.difficult = difficult;
    this.reward = reward;
    this._answers = _answers;
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

  public Map<Long, Map<String, Boolean>> get_answers() {
    return _answers;
  }

  public void set_answers(final Map<Long, Map<String, Boolean>> _answers) {
    this._answers = _answers;
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
    for (final Long id : _answers.keySet()) {
      for (final String answer : this._answers.get(id).keySet()) {
        answerSet.add(new Answer(answer, this._answers.get(id).get(answer)));
      }
    }


    final Difficult difficult = new Difficult(getDifficult());
    return new QuestionModel(getId(), getDescription(), difficult, answerSet);
  }

  @Override
  public void update(final QuestionDTO update) {
    setDescription(update.getDescription());
    set_answers(update.get_answers());
    setDifficult(update.getDifficult());
  }
}
