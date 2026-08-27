package com.ouss.web.repository;

import com.ouss.web.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactRepoPostgresTests extends BaseIntegrationTest {

    @Autowired
    private ContactJpaRepo contactRepo;

    @Test
    public void test() {
        var contact = contactRepo.findAll().get(0);
        Assertions.assertEquals(1, contactRepo.findAll().size());
        Assertions.assertEquals("ouss", contact.getFirstName());
        Assertions.assertEquals("bou", contact.getLastName());
    }
}
