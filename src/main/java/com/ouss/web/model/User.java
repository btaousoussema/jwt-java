package com.ouss.web.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="users")
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String email;
    private String password;
    private String accessToken;

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    public User(String email) {
        this.email = email;
    }
}
