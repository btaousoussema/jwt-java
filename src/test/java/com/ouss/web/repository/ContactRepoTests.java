package com.ouss.web.repository;

import com.ouss.web.model.Contact;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ContactRepoTests {

    @Autowired
    private ContactJpaRepo contactRepo;

    @Test
    public void testInsertNewContactTest() {
        final var contact = new Contact("Ouss", "bou");

        final var insertedContact = contactRepo.save(contact);

        Assertions.assertThat(insertedContact).isNotNull();
        Assertions.assertThat(insertedContact.getId()).isGreaterThan(0);
        Assertions.assertThat(insertedContact.getFirstName()).isEqualTo(contact.getFirstName());
        Assertions.assertThat(insertedContact.getLastName()).isEqualTo(contact.getLastName());
    }

    @Test
    public void testGetOneContactTest() {

        final var contact = new Contact("Ouss", "bou");

        var insertedContact = contactRepo.save(contact);

        insertedContact = contactRepo.getReferenceById(insertedContact.getId());

        Assertions.assertThat(insertedContact).isNotNull();
        Assertions.assertThat(insertedContact.getId()).isGreaterThan(0);
        Assertions.assertThat(insertedContact.getFirstName()).isEqualTo(contact.getFirstName());
        Assertions.assertThat(insertedContact.getLastName()).isEqualTo(contact.getLastName());
    }

    @Test
    public void testGetAllContactsTest() {
        final var contact1 = new Contact("Ouss", "bou");
        final var contact2 = new Contact("Ousse", "boue");

        contactRepo.save(contact1);
        contactRepo.save(contact2);

        final var contacts = contactRepo.findAll();

        Assertions.assertThat(contacts).isNotNull();
        Assertions.assertThat(contacts.size()).isEqualTo(2);
    }

    @Test
    public void testUpdateContactTest() {
        final var contact = new Contact("Ouss", "bou");

        final var savedContact = contactRepo.save(contact);

        contact.setFirstName("updatedFirstName");
        contact.setLastName("updatedLastName");

        contactRepo.save(contact);

        final var updatedContact = contactRepo.getReferenceById(savedContact.getId());

        Assertions.assertThat(updatedContact).isNotNull();
        Assertions.assertThat(updatedContact.getFirstName()).isEqualTo(contact.getFirstName());
        Assertions.assertThat(updatedContact.getLastName()).isEqualTo(contact.getLastName());
    }
}
