package com.ouss.web.service;

import com.ouss.web.model.Contact;
import com.ouss.web.repository.ContactJpaRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

    @Mock
    ContactJpaRepo contactJpaRepo;

    @InjectMocks
    ContactService contactService;

    @Test
    public void getAllContactsTest() {
        List<Contact> contacts = new ArrayList<Contact>();
        Contact contact = new Contact("ouss", "bou");
        contact.setId(1);

        contacts.add(contact);

        contact = new Contact("sam", "payne");
        contact.setId(2);

        contacts.add(contact);

        when(contactJpaRepo.findAll()).thenReturn(contacts);

        final var response = contactService.getAllContacts();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isEqualTo(contacts);
        Assertions.assertThat(response.size()).isEqualTo(2);
    }
}
