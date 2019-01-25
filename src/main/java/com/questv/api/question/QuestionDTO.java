package com.questv.api.question;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;

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

  @NotNull String questionType;

  @NotNull
  private Map<String, Boolean> answers;


  /*default*/ QuestionDTO() { }

  /*default*/ QuestionDTO(final Long id,
                          final Long ownerId,
                          final String description,
                          final String questionType,
                          final Map<String, Boolean> answers) {

    this.id = id;
    this.ownerId = ownerId;
    this.description = description;
    this.questionType = questionType;
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

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }

  @Override
  public QuestionModel convert() {
    final Set<Answer> answerSet = new HashSet<>();
    for(final String answer : this.answers.keySet()) {
      answerSet.add(new Answer(answer, this.answers.get(answer)));
    }

    final QuestionType questionType = QuestionType
        .valueOf(getQuestionType()
            .toUpperCase());

    return new QuestionModel(getOwnerId(), getDescription(), questionType,  answerSet);
  }

  @Override
  public void update(final QuestionDTO update) {
    setDescription(update.getDescription());
    setAnswers(update.getAnswers());
    setOwnerId(update.getOwnerId());
  }
}
