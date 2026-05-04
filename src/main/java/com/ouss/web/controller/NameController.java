package com.ouss.web.controller;

import com.ouss.web.doa.ContactDOA;
import com.ouss.web.model.Contact;
import com.ouss.web.service.TokenService;
import com.ouss.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contacts")
public class NameController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/get")
    public ResponseEntity<List<Contact>> getAllNames(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "").strip();
        String id = tokenService.validateToken(token);
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            ContactDOA contactDOA = new ContactDOA();
            List<Contact> ct = contactDOA.getAllNames();
            return new ResponseEntity<>(ct, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Contact> addName(@RequestHeader("Authorization") String token,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName
            ) {
        token = token.replace("Bearer ", "").strip();
        String id = tokenService.validateToken(token);
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ContactDOA db = new ContactDOA();
        return new ResponseEntity<>(db.addName(firstName, lastName), HttpStatus.OK);
    }
}
