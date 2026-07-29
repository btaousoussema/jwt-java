package com.ouss.web.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Entity
@Builder
@Getter
@NoArgsConstructor
@Setter
@Table(name="refresh_token")
public class Refresh_Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    //@JoinColumn(name = "users", referencedColumnName = "id")
    String userId;
    String refreshToken;
    Date expires_in;
    boolean active;
}

