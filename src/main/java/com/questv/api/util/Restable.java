package com.questv.api.util;

import java.util.List;

public interface Restable<T> {

  T post(final T t);
  List<T> getAll();
  T getById(final Long tId);
  void deleteById(final Long tId);
  void put(final T t);


}
