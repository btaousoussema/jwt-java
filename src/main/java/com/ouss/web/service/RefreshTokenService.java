package com.ouss.web.service;

import com.ouss.web.config.SecretConfig;
import com.ouss.web.doa.RefreshTokenDOA;
import com.ouss.web.model.RefreshToken;
import com.ouss.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
public class RefreshTokenService {

    @Autowired
    private RefreshTokenDOA refreshTokenDOA;

    @Autowired
    SecretConfig secretConfig;

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenDOA.getRefreshToken(token);
        if(refreshToken == null) {
            return null;
        }

        if(!refreshToken.isActive()) {
            refreshTokenDOA.invalidateAllRefreshToken(refreshToken.getUserId());
            return null;
        }

        if(refreshToken.getExpires_in().after(new Date(System.currentTimeMillis()))) {
            refreshTokenDOA.invalidateRefreshToken(token);
            return generateRefreshToken(refreshToken.getUserId());
        }
        return refreshToken;
    }

    public void invalidateToken(String token) {
        refreshTokenDOA.invalidateRefreshToken(token);
    }

    public String generateToken(String userId) {
        String refreshToken = UUID.randomUUID().toString();
        refreshTokenDOA.createRefreshToken(userId, refreshToken);
        return refreshToken;
    }

    public RefreshToken generateRefreshToken(String userId) {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(token);
        refreshTokenDOA.createRefreshToken(userId, token);
        return refreshToken;
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenDOA.getRefreshToken(refreshToken);
    }
}
