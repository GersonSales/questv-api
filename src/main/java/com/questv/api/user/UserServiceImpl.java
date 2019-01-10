package com.questv.api.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
        assert this.userRepository != null;
    }

    @Override
    public void create(final UserDTO userDTO) {
        this.userRepository.save(userDTO.convert());
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
}
