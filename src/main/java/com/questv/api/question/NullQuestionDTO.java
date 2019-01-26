package com.questv.api.question;

import java.util.HashMap;

public final class NullQuestionDTO extends QuestionDTO {
  public NullQuestionDTO() {
    super(0L, 0L, "NaD",  0, new HashMap<>());
  }
}
