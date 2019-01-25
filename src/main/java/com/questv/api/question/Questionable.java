package com.questv.api.question;

import java.util.Set;

public interface Questionable {
    void attachQuestion(final Question question);
    void attachAll(Set<Question> questionSet);

    Set<Question> getQuestions();

}
