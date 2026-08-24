package com.ouss.web.service;

import com.ouss.web.model.User;
import com.ouss.web.repository.UserDOA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserDOA userDOA;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


    public User authenticateUser(User user) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        User userFound = userDOA.getUser(user.getEmail());
        userFound.setAccessToken(tokenService.generateToken(authentication));
        userFound.setPassword("");

        return userFound;
    }
}
