package com.ouss.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ouss.web.config.SecretConfig;
import com.ouss.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    SecretConfig secretConfig;


    public String generateToken(User user){
        return generateToken(user.getId(), 0);
    }

    public String generateToken(String userId, int time){
        Algorithm algorithm = Algorithm.HMAC256(secretConfig.getKey());
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .sign(algorithm);
    }

    public String generateToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(secretConfig.getKey());
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(authentication.getName())
                .withExpiresAt(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }
}
