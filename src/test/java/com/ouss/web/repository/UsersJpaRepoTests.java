package com.ouss.web.repository;

import com.ouss.web.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsersJpaRepoTests {

    @Autowired
    private UsersJpaRepo usersRepo;

    @Test
    public void addUserTest() {
        final var user = User.builder()
                .email("ouss@hotmail.com")
                .password("test")
                .build();

        final var savedUser = usersRepo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void getInsertedUserTest() {
        final var user = User.builder()
                .email("ouss@hotmail.com")
                .password("test")
                .build();

        final var savedUser = usersRepo.save(user);
        final var returnedUser = usersRepo.getOne(savedUser.getId());

        Assertions.assertThat(returnedUser).isNotNull();
        Assertions.assertThat(returnedUser.getId()).isEqualTo(savedUser.getId());
        Assertions.assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(returnedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void getAllUsersTest() {
        final var user1 = User.builder()
                .email("ouss@hotmail.com")
                .password("test")
                .build();
        final var user2 = User.builder()
                .email("ouss2@hotmail.com")
                .password("test2")
                .build();

        final var savedUser1 = usersRepo.save(user1);
        final var savedUser2 = usersRepo.save(user2);

        final var allUsers = usersRepo.findAll();

        Assertions.assertThat(allUsers).isNotNull();
        Assertions.assertThat(allUsers.size()).isEqualTo(2);
        Assertions.assertThat(allUsers.contains(savedUser1)).isTrue();
        Assertions.assertThat(allUsers.contains(savedUser2)).isTrue();
    }

    @Test
    public void updateUserTest() {
        final var user = User.builder()
                .email("ouss@hotmail.com")
                .password("test")
                .build();

        final var savedUser1 = usersRepo.save(user);
        user.setPassword("updatedPassword");
        user.setEmail("updatedEmail");
        usersRepo.save(user);

        final var updatedUser = usersRepo.getUserById(savedUser1.getId()).get();

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
    }
}
