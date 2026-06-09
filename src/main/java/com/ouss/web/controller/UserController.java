package com.ouss.web.controller;

import com.ouss.web.model.User;
import com.ouss.web.service.RefreshTokenService;
import com.ouss.web.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    RefreshTokenService refreshTokenService;



    Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(user == null || user.getPassword() == null || user.getPassword().length()<6 || user.getEmail() == null){
            logger.error("Invalid user provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User createdUser = customUserDetailsService.createUser(user.getEmail(), user.getPassword());
        ResponseEntity<User> response =  new ResponseEntity<>(createdUser, HttpStatus.OK);
        return response;
    }
}
