package com.questv.api.util;

import java.util.List;

public interface ObjectService<T> {

    void createAndAttach(final Long superId, T model);

    T create(final T model);

    List<T> findAll();

    T findById(final Long tId);

    void updateById(final Long tId, final T t);

    void deleteById(final Long tId);
}
