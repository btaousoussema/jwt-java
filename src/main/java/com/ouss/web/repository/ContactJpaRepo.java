package com.ouss.web.repository;

import com.ouss.web.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactJpaRepo extends JpaRepository<Contact,Integer> {
    Optional<Contact> findContactById(int ContactId);
}
