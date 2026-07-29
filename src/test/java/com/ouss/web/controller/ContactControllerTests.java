package com.ouss.web.controller;

import com.ouss.web.model.Contact;
import com.ouss.web.security.AuthTokenFilter;
import com.ouss.web.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContactControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ContactService contactService;

    @MockitoBean
    AuthTokenFilter authTokenFilter;

    @Test
    public void testGetAllContacts() throws Exception {
        final var contact = new Contact("ouss", "bou");
        contact.setId(1);
        final var contacts = new ArrayList<Contact>();
        contacts.add(contact);

        given(contactService.getAllContacts()).willAnswer(invocationOnMock -> contacts);

        final var response = mockMvc.perform(get("/contacts"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
