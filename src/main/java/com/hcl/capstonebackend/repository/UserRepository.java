package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUsersByUsername(String username);
    Optional<User> findByUsername (String username);
}
