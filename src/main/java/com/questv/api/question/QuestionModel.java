package com.questv.api.question;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;
import com.questv.api.question.difficult.Difficult;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Embeddable
@Entity
@Table(name = "question_table", schema = "questv_schema")
public final class QuestionModel implements Convertible<QuestionDTO>, Updatable<QuestionModel> {


  @Id
  @NotNull
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String id;

  @NotNull
  private String ownerId;

  @NotEmpty
  private String description;

  @NotNull
  @OneToOne(cascade = CascadeType.ALL)
  private Difficult difficult;


  @NotNull
  @Embedded
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL)
  private Set<Answer> answerSet;

  /*default*/ QuestionModel() {
  }

  /*default*/ QuestionModel(final String id,
                            final String ownerId,
                            final String description,
                            final Difficult difficult,
                            final Set<Answer> answerSet) {
    this.id = id;
    this.ownerId = ownerId;
    this.description = description;
    this.difficult = difficult;
    this.answerSet = answerSet;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Answer> getAnswerSet() {
    return answerSet;
  }

  public void setAnswerSet(Set<Answer> answerSet) {
    this.answerSet = answerSet;
  }

  public Difficult getDifficult() {
    return difficult;
  }

  public void setDifficult(final Difficult difficult) {
    this.difficult = difficult;
  }

  @Override
  public QuestionDTO convert() {
    final Map<String, Map<String, Boolean>> answerIdsMap = new HashMap<>();
    for (final Answer answer : this.answerSet) {
      final Map<String, Boolean> answersMap = new HashMap<>();
      answersMap.put(answer.getValue(), answer.isCorrect());
      answerIdsMap.put(answer.getId(), answersMap);
    }

    final Integer difficult = getDifficult().getDifficult();
    final Integer reward = getDifficult().getReward();

    return new QuestionDTO(getId(), getOwnerId(), getDescription(), difficult, reward, answerIdsMap);
  }

  @Override
  public void update(final QuestionModel update) {
    setDescription(update.getDescription());
    setOwnerId(update.getOwnerId());
    setAnswerSet(update.getAnswerSet());
    difficult.evaluate(update.getDifficult().getDifficult());
  }
}
