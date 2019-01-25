package com.questv.api.util;

import com.questv.api.user.UserDTO;

import java.util.List;

public interface ObjectService<T> {

    UserDTO create(final T userModel);

    List<T> findAll();

    T findById(final Long tId);

    void updateById(final Long tId, final T t);

    void deleteById(final Long tId);
}
