package com.ouss.web.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class DatabaseConfig {

    @Value("${DB_USER}")
    static String  dbUser;
    @Value("${DB_PASSWORd}")
    static String dbPassword;
    @Value("${DB_NAME}")
    static String dbName;
    @Value("${DB_URL}")
    static String dbUrl;

    @PostConstruct
    public void init(@Value("${DB_USER}") String tempDbUser, @Value("${DB_PASSWORd}") String tempDbPassword,
                     @Value("${DB_NAME}") String tempDbName, @Value("${DB_URL}") String tempDbUrl){
        this.dbUser = tempDbUser;
        this.dbPassword = tempDbPassword;
        this.dbName = tempDbName;
        this.dbUrl = tempDbUrl;
    }
}
