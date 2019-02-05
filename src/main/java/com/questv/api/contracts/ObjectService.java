package com.questv.api.contracts;

import java.util.List;

public interface ObjectService<T> {

    T createAndAttach(T model);

    T create(final T model);

    List<T> findAll();

    T findById(final Long tId);

    void update(final T t);

    void delete(final Long tId);

    List<T> findAllByParent(final Long seriesId);
}
