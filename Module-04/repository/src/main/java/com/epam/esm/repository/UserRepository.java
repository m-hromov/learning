package com.epam.esm.repository;

import com.epam.esm.model.User;

import java.util.Optional;

public interface UserRepository extends CrdRepository<Long, User> {
    Optional<User> findByUsername(String username);
}
