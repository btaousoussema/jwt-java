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
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");;
        String dbName = System.getenv("DB_NAME");
        String dbUrl = System.getenv("DB_URL");
        if(dbUser == null || dbPassword == null || dbUrl.isEmpty() || dbPassword.isEmpty()) {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dbUser = dotenv.get("DB_USER");
            dbPassword = dotenv.get("DB_PASSWORD");
            dbName = dotenv.get("DB_NAME");
            dbUrl = dotenv.get("DB_URL");
            System.out.println(dbUser + dbPassword + dbUrl.isEmpty() + dbPassword);
            if (dbUser == null || dbPassword == null || dbUrl.isEmpty() || dbPassword.isEmpty()) {
                System.out.println(dbUser + " " + dbPassword + " " + dbName + " " + dbUrl);
                System.err.println("Error: No database connection possible.");
            }
        }
        //String jdbc = "jdbc:postgresql://" + dbUrl + ":5432/" + dbName;
        String jdbc = dbUrl;// + "/" + dbName;
        //String jdbc = "jdbc:postgresql://localhost:5432/Web" ;

        try {
            //connection = DriverManager.getConnection(jdbc, "ouss", "password");
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
