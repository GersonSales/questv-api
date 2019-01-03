package com.questv.serie;

import com.questv.question.Question;
import com.questv.question.Questionable;

import java.util.HashSet;
import java.util.Set;

public class Serie implements Questionable {
    private final String name;
    private final Set<Question> questions;

    public Serie(final String name) {
        this.name = name;
        questions = new HashSet<Question>();
    }

    public void attachQuestion(final Question question) {
        this.questions.add(question);
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }
}
