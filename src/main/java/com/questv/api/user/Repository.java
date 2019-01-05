package com.questv.api.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Repository extends CrudRepository<User, Long> {
    List<User> findByName(final String name);
    User findByEmail(final String email);
    User findById(final long id);
}
