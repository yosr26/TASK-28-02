package com.yosrabroug.banking.repositories;

import com.yosrabroug.banking.models.Account;
import com.yosrabroug.banking.models.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse,Integer> {
}
