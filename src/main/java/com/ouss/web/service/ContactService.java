package com.ouss.web.service;

import com.ouss.web.doa.ContactDOA;
import com.ouss.web.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactDOA contactDOA;

    public List<Contact> getContacts() {
        return contactDOA.getAllContacts();
    }

    public Contact getContact(String id) {
        return contactDOA.getContact(id);
    }

    public Contact addContact(Contact contact) {
        return contactDOA.addContact(contact);
    }
}
