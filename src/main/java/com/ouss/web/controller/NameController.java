package com.ouss.web.controller;

import com.ouss.web.doa.ContactDOA;
import com.ouss.web.model.Contact;
import com.ouss.web.service.ContactService;
import com.ouss.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/contacts")
public class NameController {

    @Autowired
    TokenService tokenService;

    @Autowired
    ContactService contactService;

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getAllNamesWithToken(@PathVariable String id) {
        Contact contact = contactService.getContact(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllNames(@CookieValue("refreshToken") String refreshToken) {
        List<Contact> contacts = contactService.getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> addName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        var contact = contactService.addContact(new Contact(firstName, lastName));
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
