package com.ouss.web.controller;

import com.ouss.web.model.Contact;
import com.ouss.web.service.ContactService;
import com.ouss.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        Contact contact = contactService.getContact(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        var contact = contactService.addContact(new Contact(firstName, lastName));
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
