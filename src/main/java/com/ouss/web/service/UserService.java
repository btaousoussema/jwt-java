package com.ouss.web.service;

import com.ouss.web.model.User;
import com.ouss.web.doa.UserDOA;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserDOA userDOA;

    @Autowired
    TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(UserService.class);


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User createUser(String email, String password) {
        return userDOA.createUser(email, password);
    }

    public String authenticateUser(User userrequest) {
        User user = userDOA.getUser(userrequest.getEmail());
        if(user == null){
            logger.error("User for " +  user.getEmail()  + " not found.");
            return "";
        }
        boolean isAuthenticated = encoder.matches(userrequest.getPassword(), user.getPassword());
        if(isAuthenticated) {
            userrequest.setId(user.getId());
            return tokenService.generateToken(user);
        }
        logger.error("User not authenticated: " +  user.getEmail());
        return "";
    }
}
