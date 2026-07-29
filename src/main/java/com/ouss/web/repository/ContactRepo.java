package com.ouss.web.repository;

import com.ouss.web.model.Contact;
import com.ouss.web.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepo {

    @Autowired
    RedisTemplate  redisTemplate;

    Logger logger = LoggerFactory.getLogger(ContactRepo.class);

    public List<Contact> getAllContacts() {
        try{
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("Select * from contacts");
            List<Contact> contacts = new ArrayList<Contact>();
            while(stmt.getResultSet().next()){
                Contact ct = new Contact(stmt.getResultSet().getInt(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
                contacts.add(ct);
            }
            return contacts;
        } catch (SQLException exception){
            logger.error("SQLException: {}", exception.getMessage());
            return null;
        }
    }

    public Contact getContact(String id) {
        try{
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM CONTACTS where id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));

            if(stmt.getResultSet().next()){
                return new Contact(stmt.getResultSet().getInt(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
            }
            return null;
        } catch (SQLException exception){
            logger.error("SQLException: {}", exception.getMessage());
            return null;
        }
    }

    public Contact addContact(Contact newContact) {
        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "Insert into contact (first_name, last_name) SELECT ?, ? WHERE not exists (Select 1 from contact where first_name = ? and last_name = ? ) RETURNING *";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newContact.getFirstName());
            stmt.setString(2, newContact.getLastName());
            stmt.setString(3, newContact.getFirstName());
            stmt.setString(4, newContact.getLastName());

            stmt.execute();
            if(stmt.getResultSet().next()) {
                var contact = new Contact(stmt.getResultSet().getInt(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
                return contact;
            }
            return null;
        } catch (SQLException exception) {
            logger.error("SQLException: {}", exception.getMessage());
            return null;
        }
    }
}
