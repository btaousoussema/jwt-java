package com.ouss.web.controller;

import com.google.gson.Gson;
import com.ouss.web.model.User;
import com.ouss.web.repository.UserDOA;
import com.ouss.web.security.AuthTokenFilter;
import com.ouss.web.service.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    RefreshTokenService refreshTokenService;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    UserDOA userDOA;

    @MockitoBean
    UserService userService;

    @MockitoBean
    AuthenticationManager authenticationManager;

    @MockitoBean
    ContactService contactService;

    @MockitoBean
    AuthTokenFilter authTokenFilter;

    @MockitoBean
    AuthenticationService authenticationService;

    @MockitoBean
    Gson gson;

    @Test
    void authenticateUser() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(null);

        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        when(tokenService.generateToken(any())).thenReturn(accessToken);
        when(refreshTokenService.generateToken(anyString())).thenReturn(refreshToken);

        var user = new User(5, "ouss@gy.com", "bou");
        when(authenticationService.authenticateUser(any())).thenReturn(user);

        user.setAccessToken(accessToken);

        final var localGson = new Gson();
        when(gson.toJson(any(User.class))).thenReturn(localGson.toJson(user));

        var userReq = new User("ouss@gy.com");
        userReq.setPassword("bou");

        final var response = mockMvc
                .perform(
                    post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON).content(localGson.toJson(userReq))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        user.setAccessToken(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(localGson.toJson(user), response);
    }
}