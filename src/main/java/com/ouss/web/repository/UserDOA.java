package com.ouss.web.repository;

import com.ouss.web.config.RedisConfig;
import com.ouss.web.model.User;
import com.ouss.web.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Repository
public class UserDOA {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisConfig redisConfig;

    Logger logger = LoggerFactory.getLogger(UserDOA.class);


    public User createUser(String email, String password) {

        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "Insert into users (email, password) SELECT ?, ? WHERE not exists (Select 1 from users where email = ? ) RETURNING email";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, email);

            stmt.execute();
            User user = new User();
            if(stmt.getResultSet().next()) {
                user.setEmail(stmt.getResultSet().getString(1));
                if(redisConfig.isActive()) {
                    redisTemplate.opsForHash().put(email, "user", user);
                    redisTemplate.expire(email, 30, TimeUnit.SECONDS);
                }
            }
            return user;
        } catch (SQLException exception) {
            logger.error("SQLException: " + exception.getMessage());
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
            if(redisConfig.isActive()) {
                User redisUser = (User) redisTemplate.opsForHash().get(email, "user");
                if (redisUser != null) {
                    return redisUser;
                }
            }
            if(stmt.getResultSet().next()) {
                user = new User(Integer.parseInt(stmt.getResultSet().getString(1)), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
                return user;
            }
            return null;
        } catch (SQLException exception) {
            logger.error("SQLException: {}", exception.getMessage());
            return null;
        }
    }

    public User getUserEmailFromId(String id) {
        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT email from users where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.execute();

            if(stmt.getResultSet().next()) {
                User user = new User();
                user.setEmail(stmt.getResultSet().getString(1));
                return user;
            }
            return null;
        } catch (SQLException exception) {
            logger.error("SQLException: {}", exception.getMessage());
            return null;
        }
    }

    public User getUserFromId(String id) {
        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * from users where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));

            stmt.execute();
            if(stmt.getResultSet().next()) {
                var user = new User(stmt.getResultSet().getInt(1),
                        stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
                user.setEmail(stmt.getResultSet().getString(1));
                return user;
            }
            return new User();
        } catch (SQLException exception) {
            logger.error("SQLException: {}", exception.getMessage());
            return null;
        }
    }
}
