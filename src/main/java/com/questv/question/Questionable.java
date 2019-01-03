package com.questv.question;

import java.util.Set;

public interface Questionable {
    void attachQuestion(final Question question);
    Set<Question> getQuestions();
}
