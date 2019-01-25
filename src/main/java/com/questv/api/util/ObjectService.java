package com.questv.api.util;

import java.util.List;

public interface ObjectService<T> {

    T create(final T userModel);

    List<T> findAll();

    T findById(final Long tId);

    void updateById(final Long tId, final T t);

    void deleteById(final Long tId);
}
