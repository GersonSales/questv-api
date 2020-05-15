package com.questv.api.player;

import com.questv.api.answered.question.AnsweredQuestionDTO;
import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.user.UserModel;

public class PlayerService {

//  /*default*/ void attachAnsweredQuestion(final String userId,
//                                          final AnsweredQuestionDTO answeredQuestionDTO) {
//    final AnsweredQuestionModel foundAnsweredQuestionModel
//        = this.answeredQuestionService.find(answeredQuestionDTO.convert());
//    final UserModel foundUser = findModelById(userId);
//    if (foundAnsweredQuestionModel != null) {
//      foundUser.attachAnsweredQuestion(foundAnsweredQuestionModel);
//      this.userRepository.save(foundUser);
//
//    } else {
//      final AnsweredQuestionModel savedAnsweredQuestionModel
//          = this.answeredQuestionService.create(answeredQuestionDTO.convert());
//      foundUser.attachAnsweredQuestion(savedAnsweredQuestionModel);
//      this.userRepository.save(foundUser);
//    }
//  }

//
//  public void detachAnsweredQuestion(final String userId,
//                                     final AnsweredQuestionDTO answeredQuestionModel) {
//
//    final UserModel userModel = findModelById(userId);
//    userModel.detachAnsweredQuestion(answeredQuestionModel.convert());
//    this.userRepository.save(userModel);
//  }
}
