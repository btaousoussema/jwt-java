package com.ouss.web.repository;

import com.ouss.web.model.Refresh_Token;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.sql.Date;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RefreshTokenRepoTests {

    @Autowired
    private RefreshTokenJpaRepo refreshTokenRepo;

    @Test
    public void insertRefreshTokenTest() {
        final var refreshToken = Refresh_Token.builder()
                .refreshToken(UUID.randomUUID().toString())
                .active(true)
                .expires_in(new Date(System.currentTimeMillis()))
                .build();

        final var insertedRefreshToken = refreshTokenRepo.save(refreshToken);

        Assertions.assertThat(insertedRefreshToken).isNotNull();
        Assertions.assertThat(insertedRefreshToken.getId()).isGreaterThan(0);
        Assertions.assertThat(insertedRefreshToken.getRefreshToken()).isNotNull();
        Assertions.assertThat(insertedRefreshToken.getExpires_in()).isEqualTo(refreshToken.getExpires_in());
        Assertions.assertThat(insertedRefreshToken.isActive()).isEqualTo(true);
    }

    @Test
    public void getInsertedRefreshTokenWithIdTest() {
        final var refreshToken = Refresh_Token.builder()
                .refreshToken(UUID.randomUUID().toString())
                .active(true)
                .expires_in(new Date(System.currentTimeMillis()))
                .build();

        final var savedRefreshToken = refreshTokenRepo.save(refreshToken);

        final var retrievedRefreshToken = refreshTokenRepo.findById(savedRefreshToken.getId()).get();

        Assertions.assertThat(retrievedRefreshToken).isNotNull();
        Assertions.assertThat(retrievedRefreshToken.getId()).isEqualTo(savedRefreshToken.getId());
        Assertions.assertThat(retrievedRefreshToken.getRefreshToken()).isEqualTo(savedRefreshToken.getRefreshToken());
        Assertions.assertThat(retrievedRefreshToken.getUserId()).isEqualTo(savedRefreshToken.getUserId());
        Assertions.assertThat(retrievedRefreshToken.isActive()).isEqualTo(savedRefreshToken.isActive());
        Assertions.assertThat(retrievedRefreshToken.getExpires_in()).isEqualTo(savedRefreshToken.getExpires_in());
    }

    @Test
    public void getRefreshTokenWithTokenTest() {
        final var refreshToken = Refresh_Token.builder()
                .refreshToken(UUID.randomUUID().toString())
                .active(true)
                .userId("1")
                .expires_in(new Date(System.currentTimeMillis()))
                .build();

        final var savedRefreshToken = refreshTokenRepo.save(refreshToken);

        final var retrievedRefreshToken = refreshTokenRepo.findByRefreshToken(savedRefreshToken.getRefreshToken()).get();

        Assertions.assertThat(retrievedRefreshToken).isNotNull();
        Assertions.assertThat(retrievedRefreshToken.getId()).isEqualTo(savedRefreshToken.getId());
        Assertions.assertThat(retrievedRefreshToken.getRefreshToken()).isEqualTo(savedRefreshToken.getRefreshToken());
        Assertions.assertThat(retrievedRefreshToken.getUserId()).isEqualTo(savedRefreshToken.getUserId());
        Assertions.assertThat(retrievedRefreshToken.isActive()).isEqualTo(savedRefreshToken.isActive());
        Assertions.assertThat(retrievedRefreshToken.getExpires_in()).isEqualTo(savedRefreshToken.getExpires_in());
    }
}
