package com.yosrabroug.banking.repositories;

import com.yosrabroug.banking.models.Account;
import com.yosrabroug.banking.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findAllByUserId(Integer id);
}
