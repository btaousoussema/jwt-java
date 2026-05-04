package com.ouss.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ouss.web.config.SecretConfig;
import com.ouss.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    SecretConfig secretConfig;


    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secretConfig.getKey());
        return JWT.create()
            .withIssuer("auth0")
            .withSubject(user.getId())
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
            .sign(algorithm);
    }

    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretConfig.getKey()))
                .withIssuer("auth0")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        System.out.println("Token verified : " + decodedJWT.toString());
        if(decodedJWT != null){
            return decodedJWT.getSubject();
        }
        return null;
    }
}
