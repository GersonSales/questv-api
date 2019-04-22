package com.questv.api.user;

import com.questv.api.answered.question.AnsweredQuestionDTO;
import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.answered.question.AnsweredQuestionService;
import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Rankable;
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
public class UserService implements UserDetailsService {

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

  public UserDTO create(final UserDTO model) {
    model.setPassword(bCryptPasswordEncoder.encode(model.getPassword()));
    return save(model.convert()).convert();
  }


  public List<Rankable> findAllRanked() {
    return findAllModels()
        .stream()
        .map(this::getNewRankable)
        .distinct()
        .collect(Collectors.toList());
  }

  private Rankable getNewRankable(final Rankable rankable) {
    return new Rankable() {
      @Override
      public String getUsername() {
        return rankable.getUsername();
      }

      @Override
      public Integer getPoints() {
        return rankable.getPoints();
      }

      @Override
      public String getId() {
        return rankable.getId();
      }
    };
  }


  public List<UserDTO> findAll() {
    final List<UserModel> result = new ArrayList<>();
    this.userRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(UserModel::convert)
        .collect(Collectors.toList());
  }

  private List<UserModel> findAllModels() {
    final List<UserModel> result = new ArrayList<>();
    this.userRepository.findAll().forEach(result::add);
    return result;
  }

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

  public void delete(final String userId) {
    this.userRepository.deleteById(userId);
  }

  public UserDTO createAndAttach(UserDTO model) {
    return model;
  }

  public List<UserDTO> findAllByParent(String seriesId) {
    return new ArrayList<>();
  }

  /*default*/ void attachAnsweredQuestion(final String userId,
                                          final AnsweredQuestionDTO answeredQuestionDTO) {
    final AnsweredQuestionModel foundAnsweredQuestionModel
        = this.answeredQuestionService.find(answeredQuestionDTO.convert());
    final UserModel foundUser = findModelById(userId);
    if (foundAnsweredQuestionModel != null) {
      foundUser.attachAnsweredQuestion(foundAnsweredQuestionModel);
      this.userRepository.save(foundUser);

    } else {
      final AnsweredQuestionModel savedAnsweredQuestionModel
          = this.answeredQuestionService.create(answeredQuestionDTO.convert());
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

  public void detachAnsweredQuestion(final String userId,
                                     final AnsweredQuestionDTO answeredQuestionModel) {

    final UserModel userModel = findModelById(userId);
    userModel.detachAnsweredQuestion(answeredQuestionModel.convert());
    this.userRepository.save(userModel);
  }
}
