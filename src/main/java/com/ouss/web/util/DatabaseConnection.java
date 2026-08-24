package com.ouss.web.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {
    private static Connection connection = null;

    static
    {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String dbUser = System.getenv("DB_USER") == null ?
                dotenv.get("DB_USER") : System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD") == null ?
                        dotenv.get("DB_PASSWORD") : System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME") == null ?
                dotenv.get("DB_NAME") : System.getenv("DB_NAME");
        String dbUrl = System.getenv("DB_URL") == null?
                dotenv.get("DB_URL") : System.getenv("DB_URL");
        if(dbUser == null || dbPassword == null || dbUrl.isEmpty() || dbPassword.isEmpty()) {
            System.err.println("Error: No database connection possible.");
            throw new RuntimeException("Database configuration is missing.");
        }

        String jdbc = "jdbc:postgresql://" + dbUrl + ":5432/" + dbName;

        try {
            connection = DriverManager.getConnection(jdbc, dbUser, dbPassword);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return connection;
    }
}
