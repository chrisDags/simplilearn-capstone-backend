package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUsersByUsername(String username);
    User findByUsername (String username);
}