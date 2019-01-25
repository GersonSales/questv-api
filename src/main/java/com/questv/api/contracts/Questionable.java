package com.questv.api.contracts;

import com.questv.api.question.QuestionModel;

import java.util.Set;

public interface Questionable {
    void attachQuestion(final QuestionModel questionModel);
    void attachAll(final Set<QuestionModel> questionModelSet);
    void detachQuestion(final QuestionModel questionModel);
    Set<QuestionModel> getQuestions();

}
