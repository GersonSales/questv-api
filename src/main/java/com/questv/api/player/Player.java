package com.questv.api.player;

import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.contracts.Rankable;
import com.questv.api.user.UserDTO;
import com.questv.api.user.UserModel;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.*;
import java.util.stream.Collectors;

public class Player {




  @ManyToMany
  @JoinTable(
      name = "user_answered_question",
      joinColumns = {@JoinColumn(name = "id_user")},
      inverseJoinColumns = {@JoinColumn(name = "id_answered_question")})
  private Set<AnsweredQuestionModel> answeredQuestions;


  public Player() {
    this.answeredQuestions = new HashSet<>();
  }


  public Set<AnsweredQuestionModel> getAnsweredQuestions() {
    return answeredQuestions;
  }

  public void setAnsweredQuestions(Set<AnsweredQuestionModel> answeredQuestionModels) {
    this.answeredQuestions = answeredQuestionModels;
  }


//  @Override
//  public UserDTO convert() {
//    final Map<Long, Long> answeredQuestions = new HashMap<>();
//    for (final AnsweredQuestionModel answeredQuestionModel :
//        this.answeredQuestions) {
//      answeredQuestions.put(answeredQuestionModel.getQuestionId(),
//          answeredQuestionModel.getAnswerId());
//    }
//
//    return new UserDTO(
//        getId(),
//        getFirstName(),
//        getLastName(),
//        getUsername(),
//        getEmail(),
//        getPassword(),
//        answeredQuestions);
//  }

  /*default
   * */ void attachAnsweredQuestion(final AnsweredQuestionModel answeredQuestionModel) {
    for (final AnsweredQuestionModel answeredQuestion :
        getAnsweredQuestions()) {
      if (answeredQuestion.getQuestionId().equals(answeredQuestionModel.getQuestionId())) {
        answeredQuestion.setAnswerId(answeredQuestionModel.getAnswerId());
        return;
      }
    }
    this.answeredQuestions.add(answeredQuestionModel);
  }


  public void detachAnsweredQuestion(final AnsweredQuestionModel answeredQuestion) {
    this.answeredQuestions.remove(answeredQuestion);
  }

//  public List<Rankable> findAllRanked() {
//    return findAllModels()
//        .stream()
//        .map(this::getNewRankable)
//        .distinct()
//        .sorted()
//        .collect(Collectors.toList());
//  }
//
//  private Rankable getNewRankable(final UserModel userModel) {
//    return new Rankable() {
//      @Override
//      public String getUsername() {
//        return userModel.getUsername();
//      }
//
//      @Override
//      public Integer getPoints() {
//        return 0;//answeredQuestionService.calculatePoints(userModel
//        // .getAnsweredQuestions());
//      }
//
//      @Override
//      public String getId() {
//        return userModel.getId();
//      }
//    };
//  }



}
