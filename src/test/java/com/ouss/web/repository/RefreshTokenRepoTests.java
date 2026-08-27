package com.ouss.web.repository;

import com.ouss.web.BaseIntegrationTest;
import com.ouss.web.model.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class RefreshTokenRepoTests extends BaseIntegrationTest {

    @Autowired
    private RefreshTokenJpaRepo refreshTokenRepo;

    @Test
    public void insertRefreshTokenTest() {
        final var refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .active(true)
                .expires_at(Timestamp.from(Instant.ofEpochSecond(System.currentTimeMillis())))
                .build();

        final var insertedRefreshToken = refreshTokenRepo.save(refreshToken);

        Assertions.assertThat(insertedRefreshToken).isNotNull();
        Assertions.assertThat(insertedRefreshToken.getId()).isGreaterThan(0);
        Assertions.assertThat(insertedRefreshToken.getRefreshToken()).isNotNull();
        Assertions.assertThat(insertedRefreshToken.getExpires_at()).isEqualTo(refreshToken.getExpires_at());
        Assertions.assertThat(insertedRefreshToken.isActive()).isEqualTo(true);
    }

    @Test
    public void getInsertedRefreshTokenWithIdTest() {
        final var refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .active(true)
                .expires_at(Timestamp.from(Instant.ofEpochSecond(System.currentTimeMillis())))
                .build();

        final var savedRefreshToken = refreshTokenRepo.save(refreshToken);

        final var retrievedRefreshToken = refreshTokenRepo.findById(savedRefreshToken.getId()).get();

        Assertions.assertThat(retrievedRefreshToken).isNotNull();
        Assertions.assertThat(retrievedRefreshToken.getId()).isEqualTo(savedRefreshToken.getId());
        Assertions.assertThat(retrievedRefreshToken.getRefreshToken()).isEqualTo(savedRefreshToken.getRefreshToken());
        Assertions.assertThat(retrievedRefreshToken.getUserId()).isEqualTo(savedRefreshToken.getUserId());
        Assertions.assertThat(retrievedRefreshToken.isActive()).isEqualTo(savedRefreshToken.isActive());
        Assertions.assertThat(retrievedRefreshToken.getExpires_at()).isEqualTo(savedRefreshToken.getExpires_at());
    }

    @Test
    public void getRefreshTokenWithTokenTest() {
        final var refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .active(true)
                .userId("1")
                .expires_at(Timestamp.from(Instant.ofEpochSecond(System.currentTimeMillis())))
                .build();

        final var savedRefreshToken = refreshTokenRepo.save(refreshToken);

        final var retrievedRefreshToken = refreshTokenRepo.findByRefreshToken(savedRefreshToken.getRefreshToken()).get();

        Assertions.assertThat(retrievedRefreshToken).isNotNull();
        Assertions.assertThat(retrievedRefreshToken.getId()).isEqualTo(savedRefreshToken.getId());
        Assertions.assertThat(retrievedRefreshToken.getRefreshToken()).isEqualTo(savedRefreshToken.getRefreshToken());
        Assertions.assertThat(retrievedRefreshToken.getUserId()).isEqualTo(savedRefreshToken.getUserId());
        Assertions.assertThat(retrievedRefreshToken.isActive()).isEqualTo(savedRefreshToken.isActive());
        Assertions.assertThat(retrievedRefreshToken.getExpires_at()).isEqualTo(savedRefreshToken.getExpires_at());
    }
}
