package com.questv.api.contracts;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Restable<T> {

  ResponseEntity<T> post(final T t);
  ResponseEntity<List<T>> get();
  ResponseEntity<T> get(final Long tId);
  void delete(final Long tId);
  void put(final T t);


}
