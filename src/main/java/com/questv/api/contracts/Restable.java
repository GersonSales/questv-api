package com.questv.api.contracts;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Restable<T> {

  ResponseEntity<T> post(final T t);
  ResponseEntity<List<T>> get();
  ResponseEntity<T> get(final String tId);
  void delete(final String tId);
  void put(final String id, final T t);


}
