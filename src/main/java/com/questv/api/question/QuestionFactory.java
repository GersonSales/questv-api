package com.questv.api.question;

import java.util.HashSet;
import java.util.Set;

public class QuestionFactory {
  public static Question createQuestion(final String description, final String correctAnswer, final String... wrongAnswers) {
    final Set<Answer> answers = new HashSet<>();
    answers.add(new Answer(correctAnswer, true));
    for (final String wrong : wrongAnswers) {
      answers.add(new Answer(wrong, false));
    }
    return new Question(description, answers);
  }
}
