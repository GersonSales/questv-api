package com.questv.api.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findById(final String id);
    UserModel findByUsername(final String username);
    void deleteById(final String id);

}
