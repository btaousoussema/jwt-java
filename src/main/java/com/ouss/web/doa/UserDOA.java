package com.ouss.web.doa;

import com.ouss.web.model.User;
import com.ouss.web.util.DatabaseConnection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserDOA {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public User createUser(String email, String password) {

        try{
            String hashedPassword = encoder.encode(password);
            Connection conn = DatabaseConnection.getConnection();
            String sql = "Insert into users (email, password) SELECT ?, ? WHERE not exists (Select 1 from users where email = ? ) RETURNING email";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);

            stmt.execute();
            User user = new User();
            if(stmt.getResultSet().next()) {
                user.setEmail(stmt.getResultSet().getString(1));
            }
            return user;
        } catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
            return null;
        }
    }

    public User getUser(String email) {

        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * from users where email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            stmt.execute();
            User user = new User();
            if(stmt.getResultSet().next()) {
                user = new User(stmt.getResultSet().getString(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
            }
            return user;
        } catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
            return null;
        }
    }

    public User getUserEmail(String email) {

        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT email from users where email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            stmt.execute();
            User user = new User();
            if(stmt.getResultSet().next()) {
                user.setEmail(stmt.getResultSet().getString(1));
            }
            return user;
        } catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
            return null;
        }
    }
}
