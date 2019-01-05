package com.questv.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService{


    @Autowired
    UserRepository userRepository;

    @Override
    public void create(final UserModel userModel) {
        this.userRepository.save(userModel);
    }
}
