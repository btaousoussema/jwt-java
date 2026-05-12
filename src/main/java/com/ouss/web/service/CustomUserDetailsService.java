package com.ouss.web.service;

import com.ouss.web.model.User;
import com.ouss.web.doa.UserDOA;
import com.ouss.web.service.queue.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDOA userDOA;

    @Autowired
    TokenService tokenService;

    @Autowired
    MessageService messageService;

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User createUser(String email, String password) {
        User user = userDOA.createUser(email, encoder.encode(password));
        messageService.sendAccountCreation(user.getEmail());
        return user;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userDOA.getUser(email);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
