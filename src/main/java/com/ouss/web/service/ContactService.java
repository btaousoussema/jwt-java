package com.ouss.web.service;

import com.ouss.web.repository.ContactJpaRepo;
import com.ouss.web.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactJpaRepo contactJpaRepo;

    public List<Contact> getAllContacts() {
        return contactJpaRepo.findAll();
    }

    public Contact getContact(String id) {
        return contactJpaRepo.getReferenceById(Integer.parseInt(id));
    }

    public Contact addContact(Contact contact) {
        return contactJpaRepo.save(contact);
    }
}
