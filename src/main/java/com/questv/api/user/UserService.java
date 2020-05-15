package com.questv.api.user;

import com.questv.api.answered.question.AnsweredQuestionService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.exception.user.UserEmailAlreadyInUseException;
import com.questv.api.exception.user.UserEmailNotFoundException;
import com.questv.api.exception.user.UserNotFoundException;
import com.questv.api.exception.user.UsernameAlreadyInUserException;
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

  public UserService(final UserRepository userRepository,
                     final AnsweredQuestionService answeredQuestionService) {
    this.userRepository = userRepository;
    assert this.userRepository != null;
  }

  public UserDTO create(final UserDTO userDTO) {
    if (isEmailAlreadyUsed(userDTO.getEmail())) {
      throw new UserEmailAlreadyInUseException();
    }

    if (isUsernameAlreadyUsed(userDTO.getUsername())) {
      throw new UsernameAlreadyInUserException();
    }

    userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
    return save(userDTO.convert()).convert();
  }

  private UserModel findByEmail(final String email) {
    final Optional<UserModel> byEmail = this.userRepository.findByEmail(email);
    if (byEmail.isPresent()) {
      return byEmail.get();
    }
    throw new UserEmailNotFoundException();
  }


  private boolean isUsernameAlreadyUsed(final String username) {
    try {
      loadUserByUsername(username);
      return true;
    } catch (final UserNotFoundException exception) {
      return false;
    }
  }


  private boolean isEmailAlreadyUsed(final String email) {
    try {
      findByEmail(email);
      return true;
    } catch (final UserEmailNotFoundException exception) {
      return false;
    }
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


  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final UserModel byUsername = this.userRepository.findByUsername(username);
    if (byUsername == null) {
      throw new UserNotFoundException();
    }
    return byUsername;
  }


}
