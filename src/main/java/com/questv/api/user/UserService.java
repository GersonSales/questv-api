package com.questv.api.user;

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

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
    assert this.userRepository != null;
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
}
