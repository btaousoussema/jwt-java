package com.ouss.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken {
    String id;
    String userId;
    String refreshToken;
    Date expires_in;
    boolean active;
}
