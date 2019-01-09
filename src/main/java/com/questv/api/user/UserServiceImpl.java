package com.questv.api.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
        assert this.userRepository != null;
    }

    @Override
    public void create(final UserModel userModel) {
        this.userRepository.save(userModel);
        System.out.println("User successful created.");  // TODO (remove)
    }

    @Override
    public List<UserModel> findAll() {
        final List<UserModel> result = new ArrayList<>();
        this.userRepository.findAll().forEach(result::add);
        return result;

    }
}
