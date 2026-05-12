package com.ouss.web.controller;

import com.ouss.web.model.User;
import com.ouss.web.service.TokenService;
import com.ouss.web.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println(user.toString());
        User createdUser = customUserDetailsService.createUser(user.getEmail(), user.getPassword());
        ResponseEntity<User> response =  new ResponseEntity<>(createdUser, HttpStatus.OK);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<User> authenticateUser(@RequestBody User user) {
        /*String token = userService.authenticateUser(user);
        user.setPassword("");
        if(!token.isEmpty()){
            user.setToken(token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }*/
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        var usersa = new User();
        usersa.setToken(tokenService.generateToken(authentication));
        return new ResponseEntity<>(usersa, HttpStatus.OK);
        //System.out.println("Token empty for user : " + user.getEmail());
        //return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/modify")
    public ResponseEntity<String> validateUser(@RequestParam("email") String email,
                                                   @RequestParam("token") String token) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
