package com.ouss.web.repository;


import com.ouss.web.model.Refresh_Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<Refresh_Token, Integer> {
    Optional<Refresh_Token> findById(int id);
    Optional<Refresh_Token> findByRefreshToken(String refreshToken);
}
