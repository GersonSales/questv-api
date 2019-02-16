package com.questv.api.contracts;

import java.util.List;

public interface ObjectService<T> {

    T createAndAttach(T model);

    T create(final T model);

    List<T> findAll();

    T findById(final String tId);

    void update(final T t);

    void delete(final String tId);

    List<T> findAllByParent(final String parentId);
}
