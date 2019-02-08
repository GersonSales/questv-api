package com.questv.api.user;

import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.answered.question.AnsweredQuestionService;
import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.series.SeriesModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserService implements ObjectService<UserDTO> {

  private final UserRepository userRepository;
  private final AnsweredQuestionService answeredQuestionService;

  public UserService(final UserRepository userRepository,
                     final AnsweredQuestionService answeredQuestionService) {
    this.userRepository = userRepository;
    this.answeredQuestionService = answeredQuestionService;
    assert this.userRepository != null;
    assert this.answeredQuestionService != null;
  }

  @Override
  public UserDTO create(final UserDTO model) {
    return save(model.convert()).convert();
  }

  @Override
  public List<UserDTO> findAll() {
    final List<UserModel> result = new ArrayList<>();
    this.userRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(UserModel::convert)
        .collect(Collectors.toList());
  }

  @Override
  public UserDTO findById(final Long userId) {
    return findModelById(userId).convert();
  }

  private UserModel findModelById(final Long userId) {
    final Optional<UserModel> userModel = this.userRepository.findById(userId);
    if (userModel.isPresent()) {
      return userModel.get();
    }
    throw new IdNotFoundException();
  }

  @Override
  public void update(final UserDTO userDTO) {
    update(userDTO.convert());
  }

  public void update(final UserModel userModel) {
    final UserModel foundUser = findModelById(userModel.getId());
    foundUser.update(userModel);
    save(foundUser);
  }

  private UserModel save(final UserModel userModel) {
    return this.userRepository.save(userModel);
  }

  @Override
  public void delete(final Long userId) {
    this.userRepository.deleteById(userId);
  }

  @Override
  public UserDTO createAndAttach(UserDTO model) {
    return model;
  }

  @Override
  public List<UserDTO> findAllByParent(Long seriesId) {
    return new ArrayList<>();
  }

  /*default*/ void attachAnsweredModel(final Long userId,
                                  final AnsweredQuestionModel answeredQuestionModel) {
    final AnsweredQuestionModel foundAnsweredQuestionModel
        = this.answeredQuestionService.find(answeredQuestionModel);
    final UserModel foundUser = findModelById(userId);
    if (foundAnsweredQuestionModel != null) {
      foundUser.attachAnsweredQuestion(foundAnsweredQuestionModel);
      this.userRepository.save(foundUser);

    } else {
      final AnsweredQuestionModel savedAnsweredQuestionModel
          = this.answeredQuestionService.create(answeredQuestionModel);
      foundUser.attachAnsweredQuestion(savedAnsweredQuestionModel);
      this.userRepository.save(foundUser);
    }
  }
}
