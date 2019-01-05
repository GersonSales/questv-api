package com.questv.api.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    List<UserModel> findByName(final String name);
    UserModel findByEmail(final String email);
    UserModel findById(final long id);
}
