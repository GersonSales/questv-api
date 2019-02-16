package com.questv.api.user;

import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.answered.question.AnsweredQuestionService;
import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserService implements ObjectService<UserDTO>, UserDetailsService {

  private final UserRepository userRepository;
  private final AnsweredQuestionService answeredQuestionService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserService(final UserRepository userRepository,
                     final AnsweredQuestionService answeredQuestionService,
                     final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.answeredQuestionService = answeredQuestionService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    assert this.userRepository != null;
    assert this.answeredQuestionService != null;
    assert this.bCryptPasswordEncoder != null;
  }

  @Override
  public UserDTO create(final UserDTO model) {
    model.setPassword(bCryptPasswordEncoder.encode(model.getPassword()));
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
  public UserDTO findById(final String userId) {
    return findModelById(userId).convert();
  }

  private UserModel findModelById(final String userId) {
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
  public void delete(final String userId) {
    this.userRepository.deleteById(userId);
  }

  @Override
  public UserDTO createAndAttach(UserDTO model) {
    return model;
  }

  @Override
  public List<UserDTO> findAllByParent(String seriesId) {
    return new ArrayList<>();
  }

  /*default*/ void attachAnsweredModel(final String userId,
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

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    UserModel byUsername = this.userRepository.findByUsername(username);
    if (byUsername != null) {
      return new UserPayload(byUsername.getId(), byUsername.getUsername(), byUsername.getPassword(), new ArrayList<>());
    }
    throw new UserNotFoundException();
  }
}
