package com.yosrabroug.banking.repositories;

import com.yosrabroug.banking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccRepository  extends JpaRepository<Account,Integer>{
    Optional<Account> findByIban(String iban);

    Optional<Account> findByUserId(Integer id);
}




