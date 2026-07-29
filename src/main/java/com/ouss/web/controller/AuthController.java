package com.ouss.web.controller;

import com.google.gson.Gson;
import com.ouss.web.repository.UserDOA;
import com.ouss.web.model.Refresh_Token;
import com.ouss.web.model.User;
import com.ouss.web.service.RefreshTokenService;
import com.ouss.web.service.TokenService;
import com.ouss.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserDOA userDOA;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        User userFound = userDOA.getUser(user.getEmail());
        userFound.setAccessToken(tokenService.generateToken(authentication));
        String refreshToken = refreshTokenService.generateToken(String.valueOf(userFound.getId()));
        userFound.setPassword("");
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
        HttpHeaders headers = new HttpHeaders(MultiValueMap.fromSingleValue(Map.of(HttpHeaders.SET_COOKIE, cookie.toString())));
        final var gson = new Gson();
        return new ResponseEntity<>(gson.toJson(userFound), headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@CookieValue(name = "refreshToken", defaultValue = "") String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken",  "")
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
        HttpHeaders headers = new HttpHeaders(MultiValueMap.fromSingleValue(Map.of(HttpHeaders.SET_COOKIE, cookie.toString())));
        refreshTokenService.invalidateToken(refreshToken);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<User> refreshToken(@CookieValue(name = "refreshToken", defaultValue = "") String refreshToken) {
        if(refreshToken == null ||  refreshToken.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Refresh_Token oldRefreshToken = refreshTokenService.getRefreshToken(refreshToken);

        if(oldRefreshToken == null ||  oldRefreshToken.getRefreshToken().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Refresh_Token newRefreshToken = refreshTokenService.validateRefreshToken(refreshToken);

        if(newRefreshToken == null || newRefreshToken.getRefreshToken().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ResponseCookie cookie = ResponseCookie.from("refreshToken",  newRefreshToken.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
        HttpHeaders headers = new HttpHeaders(MultiValueMap.fromSingleValue(Map.of(HttpHeaders.SET_COOKIE, cookie.toString())));
        String accessToken = tokenService.generateToken(oldRefreshToken.getUserId(), 360000);
        User user = userService.getUserFromId(oldRefreshToken.getUserId());
        user.setAccessToken(accessToken);

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

}

