package com.questv.api.contracts;

import com.questv.api.question.QuestionModel;
import org.springframework.data.annotation.Id;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

public interface Questionable {

  Long getId();

  void attachQuestion(final QuestionModel questionModel);

  void attachAll(final Set<QuestionModel> questionModelSet);

  void detachQuestion(final QuestionModel questionModel);

  Set<QuestionModel> getQuestions();

  Set<QuestionModel> getQuestionsRecursively();

  Integer getQuestionsCount();
}
