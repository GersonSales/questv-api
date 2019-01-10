package com.questv.api.user;

import java.util.List;

public interface UserService {

    void create(final UserDTO userModel);

    List<UserDTO> findAll();

    UserDTO findById(final Long userId);

    void updateById(final Long userId, final UserDTO userDTO);
}
