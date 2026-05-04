package com.ouss.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String id;
    private String email;
    private String password;
    private String token;

    public User(){};

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }
}
