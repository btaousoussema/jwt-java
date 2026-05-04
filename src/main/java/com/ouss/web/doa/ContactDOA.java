package com.ouss.web.doa;

import com.ouss.web.model.Contact;
import com.ouss.web.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDOA {

    public List<Contact> getAllNames() {
        try{
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("Select * from contact");
            List<Contact> contacts = new ArrayList<Contact>();
            while(stmt.getResultSet().next()){
                Contact ct = new Contact(stmt.getResultSet().getInt(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
                contacts.add(ct);
            }
            return contacts;
        } catch (SQLException exception){
            System.out.println("SQLException: " + exception.getMessage());
            return null;

        }
    }

    public Contact addName(String firstName, String lastName) {

        try{
            Connection conn = DatabaseConnection.getConnection();
            String sql = "Insert into contact (first_name, last_name) SELECT ?, ? WHERE not exists (Select 1 from contact where first_name = ? and last_name = ? ) RETURNING *";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);

            stmt.execute();
            Contact contact = null;
            if(stmt.getResultSet().next()) {
                contact = new Contact(stmt.getResultSet().getInt(1), stmt.getResultSet().getString(2), stmt.getResultSet().getString(3));
            }
            return contact;
        } catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
            return null;
        }
    }
}
