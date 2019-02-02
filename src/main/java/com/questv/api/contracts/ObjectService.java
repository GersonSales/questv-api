package com.questv.api.contracts;

import java.util.List;

public interface ObjectService<T> {

    void createAndAttach(T model);

    T create(final T model);

    List<T> findAll();

    T findById(final Long tId);

    void updateById(final Long tId, final T t);

    void deleteById(final Long tId);

    List<T> findAllByParent(final Long seriesId);
}
