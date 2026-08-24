package com.ouss.web.repository;


import com.ouss.web.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findById(int id);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
