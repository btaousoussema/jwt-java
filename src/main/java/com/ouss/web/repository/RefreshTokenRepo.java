package com.ouss.web.repository;

import com.ouss.web.model.RefreshToken;
import com.ouss.web.model.User;
import com.ouss.web.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Repository
public class RefreshTokenRepo {

    Logger logger = LoggerFactory.getLogger(RefreshTokenRepo.class);

    public void createRefreshToken(String userId, String refreshToken) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "Insert into refresh_token (user_id, token, expiry_date, active) SELECT ?, ?, ?, true WHERE not exists (Select 1 from refresh_token where user_id = ? and active = true ) RETURNING *";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(userId));
            stmt.setString(2, refreshToken);
            stmt.setString(3, new Date(System.currentTimeMillis() + 3600000).toString());
            stmt.setInt(4, Integer.parseInt(userId));
            stmt.execute();
        } catch (SQLException e) {
           logger.error("SQLException: " + e.getMessage());
        }
    }

    public String getRefreshToken(int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM refresh_token WHERE user_id = ? and active = true;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();

            if(stmt.getResultSet().next()) {
                String refresh_token = stmt.getResultSet().getString(2);
                return refresh_token;
            }
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
        }
        return null;
    }

    public RefreshToken getRefreshToken(String token) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM refresh_token WHERE token = ? and active = true";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.execute();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            if(stmt.getResultSet().next()) {
                try {
                    RefreshToken refreshToken = new RefreshToken(stmt.getResultSet().getInt(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3),
                           formatter.parse(stmt.getResultSet().getString(4)), stmt.getResultSet().getBoolean(5));
                    return refreshToken;
                } catch (ParseException e) {
                    logger.error("Parse exception: {}", e.getMessage());
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException: {}", e.getMessage());
        }
        return null;
    }

    public User getUserFromRefreshToken(String token) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM refresh_token WHERE token = ? and active = true;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.execute();

            if(stmt.getResultSet().next()) {
                User user = new User();
                user.setId(stmt.getResultSet().getInt(1));
                return user;
            }
        } catch (SQLException e) {
            logger.error("SQLException: {}", e.getMessage());
        }
        return null;
    }

    public void invalidateAllRefreshToken(String userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE refresh_token SET active = false WHERE user_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.execute();
        } catch (SQLException e) {
            logger.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void invalidateRefreshToken(String token) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            logger.info("Trying to invalidate refresh token : {}", token);
            String sql = "UPDATE refresh_token SET active = false WHERE token = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.execute();
        } catch (SQLException e) {
            logger.error("SQLException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
