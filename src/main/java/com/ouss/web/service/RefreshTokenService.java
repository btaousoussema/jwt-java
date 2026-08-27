package com.ouss.web.service;

import com.ouss.web.repository.RefreshTokenJpaRepo;
import com.ouss.web.model.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Component
public class RefreshTokenService {

    @Autowired
    private RefreshTokenJpaRepo refreshTokenRepo;

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepo.findByRefreshToken(token).orElse(null);
        if(refreshToken == null) {
            return null;
        }

        if(!refreshToken.isActive()) {
            refreshTokenRepo.getReferenceById(refreshToken.getId()).setActive(false);
            //refreshTokenRepo.invalidateAllRefreshToken(refreshToken.getUserId());
            return null;
        }

        if(refreshToken.getExpires_at().after(new Date(System.currentTimeMillis()))) {
            var expiredRefreshToken = refreshTokenRepo.getReferenceById(refreshToken.getId());
            expiredRefreshToken.setActive(false);
            refreshTokenRepo.save(expiredRefreshToken);
            //refreshTokenRepo.invalidateRefreshToken(token);
            return generateRefreshToken(refreshToken.getUserId());
        }
        return refreshToken;
    }

    public void invalidateToken(String token) {
        var refreshToken = refreshTokenRepo.findByRefreshToken(token);
        if(refreshToken.isPresent()) {
            refreshToken.get().setActive(false);
            refreshTokenRepo.save(refreshToken.get());
        }
    }

    public RefreshToken generateRefreshToken(String userId) {
        String token = UUID.randomUUID().toString();
        final var refreshToken = RefreshToken.builder()
                .userId(userId)
                .refreshToken(token)
                .active(true)
                .expires_at(Timestamp.from(Instant.ofEpochSecond(System.currentTimeMillis() + 300000)))
                .build();
        refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(refreshToken).orElse(null);
    }
}
