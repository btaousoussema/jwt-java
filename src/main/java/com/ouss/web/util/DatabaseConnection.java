package com.ouss.web.util;

import com.ouss.web.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {
    private static Connection con = null;


    /*@Value("${DB_USER}")
    private static String dbUser;
    @Value("${DB_PASSWORd}")
    private static String dbPassword;
    @Value("${DB_NAME}")
    private static String dbName;
    @Value("${DB_URL}")
    private static String dbUrl;*/
    /*@Autowired
    private static DatabaseConfig databaseConfig;*/

    static
    {
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");;
        String dbName = System.getenv("DB_NAME");
        String dbUrl = System.getenv("DB_URL");
        if(dbUser == null || dbPassword == null || dbUrl.isEmpty() || dbPassword.isEmpty()) {
            System.err.println("Error: No database connection possible.");
        }
        String jdbc = "jdbc:postgresql://" + dbUrl + ":5432/" + dbName;
        try {
            con = DriverManager.getConnection(jdbc, dbUser, dbPassword);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return con;
    }
}
