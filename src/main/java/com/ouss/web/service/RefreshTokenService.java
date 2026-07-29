package com.ouss.web.service;

import com.ouss.web.config.SecretConfig;
import com.ouss.web.repository.RefreshTokenJpaRepo;
import com.ouss.web.model.Refresh_Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
public class RefreshTokenService {

    @Autowired
    private RefreshTokenJpaRepo refreshTokenRepo;

    @Autowired
    SecretConfig secretConfig;

    public Refresh_Token validateRefreshToken(String token) {
        Refresh_Token refreshToken = refreshTokenRepo.findByRefreshToken(token).orElse(null);
        if(refreshToken == null) {
            return null;
        }

        if(!refreshToken.isActive()) {
            refreshTokenRepo.getReferenceById(refreshToken.getId()).setActive(false);
            //refreshTokenRepo.invalidateAllRefreshToken(refreshToken.getUserId());
            return null;
        }

        if(refreshToken.getExpires_in().after(new Date(System.currentTimeMillis()))) {
            refreshTokenRepo.getReferenceById(refreshToken.getId()).setActive(false);
            //refreshTokenRepo.invalidateRefreshToken(token);
            return generateRefreshToken(refreshToken.getUserId());
        }
        return refreshToken;
    }

    public void invalidateToken(String token) {
        var refreshToken = refreshTokenRepo.findByRefreshToken(token);
        if(refreshToken.isPresent()) {
            refreshToken.get().setActive(false);
            //refreshTokenRepo.delete(refreshToken);
        }
    }

    public String generateToken(String userId) {
        String token = UUID.randomUUID().toString();
        final var refreshToken = Refresh_Token.builder()
                .userId(userId)
                .refreshToken(token)
                .active(true)
                .build();
        refreshTokenRepo.save(refreshToken);
        return token;
    }

    public Refresh_Token generateRefreshToken(String userId) {
        String token = UUID.randomUUID().toString();
        Refresh_Token refreshToken = new Refresh_Token();
        refreshToken.setRefreshToken(token);
        refreshToken.setUserId(userId);
        refreshToken.setActive(true);
        refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public Refresh_Token getRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(refreshToken).orElse(null);
    }
}
