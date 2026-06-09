package com.ouss.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ouss.web.config.SecretConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JWTUtil {

    @Autowired
    SecretConfig secretConfig;

    public boolean isValidToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretConfig.getKey()))
                .withIssuer("auth0")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return (decodedJWT == null) ? false : true;
    }

    public String retrieveUserFromToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretConfig.getKey()))
                .withIssuer("auth0")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        if(decodedJWT != null){
            return decodedJWT.getSubject();
        }
        return null;
    }
}
