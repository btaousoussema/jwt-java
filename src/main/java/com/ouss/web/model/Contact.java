package com.ouss.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    public Contact(int id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    private int id;
    private String lastName;
    private String firstName;
}
