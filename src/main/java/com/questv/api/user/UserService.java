package com.questv.api.user;

import java.util.List;

public interface UserService {

    void create(final UserModel userModel);

    List<UserModel> findAll();
}
