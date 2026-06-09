package com.ouss.web.util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {
    private static Connection con = null;

    static
    {
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");;
        String dbName = System.getenv("DB_NAME");
        String dbUrl = System.getenv("DB_URL");
        if(dbUser == null || dbPassword == null || dbUrl.isEmpty() || dbPassword.isEmpty()) {
            System.err.println("Error: No database connection possible.");
        }
        //String jdbc = "jdbc:postgresql://" + dbUrl + ":5432/" + dbName;
        String jdbc = "jdbc:postgresql://localhost:5432/Web" ;
        try {
            //con = DriverManager.getConnection(jdbc, dbUser, dbPassword);
            con = DriverManager.getConnection(jdbc, "ouss", "password");
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
