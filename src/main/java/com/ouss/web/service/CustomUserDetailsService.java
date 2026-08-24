package com.ouss.web.service;

import com.ouss.web.model.User;
import com.ouss.web.repository.UserDOA;
import com.ouss.web.service.queue.MessageService;
import lombok.extern.slf4j.Slf4j;
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

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User createUser(String email, String password) {
        User user = userDOA.createUser(email, encoder.encode(password));
        messageService.sendEmailAccountCreation(user.getEmail());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String identifiant) throws UsernameNotFoundException {
        var user = userDOA.getUser(identifiant);
        if(user == null){
            user = userDOA.getUserFromId(identifiant);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
