package com.questv.api.user;

import com.questv.api.contracts.ObjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserServiceImpl implements ObjectService<UserDTO> {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
        assert this.userRepository != null;
    }

    @Override
    public void updateById(final Long userId, final UserDTO userDTO) {
        this.userRepository
                .findById(userId)
                .ifPresent((userModel) -> {
                    userModel.update(userDTO);
                    this.userRepository.save(userModel);
                });
    }

    @Override
    public UserDTO create(final UserDTO model) {
        return this.userRepository.save(model.convert()).convert();

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
        Optional<UserModel> byId = this.userRepository.findById(userId);
        return byId.map(UserModel::convert).orElse(null);
    }

    @Override
    public void deleteById(final Long userId) {
        this.userRepository.deleteById(userId);

    }

    @Override
    public void createAndAttach(UserDTO model) {

    }

    @Override
    public List<UserDTO> findAllByParent(Long seriesId) {
        return null;
    }
}
