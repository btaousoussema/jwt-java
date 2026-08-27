package com.ouss.web.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@Entity
@Builder
@Getter
@NoArgsConstructor
@Setter
@Table(name="refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String userId;
    String refreshToken;
    Timestamp expires_at;
    boolean active;
}

