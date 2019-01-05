package com.questv.question;

import org.junit.Assert;
import org.junit.Test;

public class QuestionTest {

  @Test
  public void creationTest() {
    final Question question = new Question("What's your name?", correctAnswer);

    Assert.assertEquals(2, question.getReward(), 1);

    question.evaluate(10);
    Assert.assertEquals(3, question.getReward(), 1);

    question.evaluate(0);
    Assert.assertEquals(2, question.getReward(), 1);
  }

}
