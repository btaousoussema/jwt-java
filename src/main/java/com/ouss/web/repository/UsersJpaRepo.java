package com.ouss.web.repository;

import com.ouss.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersJpaRepo extends JpaRepository<User, Integer> {
    Optional<User> getUserById(int id);
}
