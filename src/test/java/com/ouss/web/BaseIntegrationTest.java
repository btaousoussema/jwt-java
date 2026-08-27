package com.ouss.web;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
public abstract class BaseIntegrationTest {

    static PostgreSQLContainer postgres = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:18.6"))
                .withDatabaseName("Web")
                .withPassword("password")
                .withInitScript("init.sql")
                .withUsername("ouss");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
