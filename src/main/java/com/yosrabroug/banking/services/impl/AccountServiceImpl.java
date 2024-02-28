package com.yosrabroug.banking.services.impl;

import com.yosrabroug.banking.dto.AccountDto;
import com.yosrabroug.banking.exceptions.OperationNotpermittedException;
import com.yosrabroug.banking.models.Account;
import com.yosrabroug.banking.repositories.AccRepository;
import com.yosrabroug.banking.services.AccountService;
import com.yosrabroug.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccRepository repository;
    private final ObjectsValidator<AccountDto> validator;
    @Override
    public Integer save(AccountDto dto){
        if(dto.getId() != null){
            throw new OperationNotpermittedException(
                    "Account cannot be updated", //error msg
                    "save account", //operationId
                    "Account", //source
                    "Updating the account is not permitted" //dependency
            );
        }
        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);
        boolean userAlreadyHasAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();
        if (userAlreadyHasAnAccount && account.getUser().isActive()){
            throw new OperationNotpermittedException(
                    "the user already has an account",
                    "create account",
                    "account service",
                    "Account creation"
            );
        }
        //create IBAN
        account.setIban(generateRandomIban());
        Account savedAccount = repository.save(account);
        return savedAccount.getId();

    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("No account was found with this id"+id));
    }

    @Override
    public void delete(Integer id) {
         repository.deleteById(id);

    }

    private String generateRandomIban(){

        //generate random iban using iban4j library
        String iban = Iban.random(CountryCode.DE).toFormattedString();

        //check if the iban already exits
        boolean ibanExists = repository.findByIban(iban).isPresent();

        //if it exits
        if (ibanExists){
            generateRandomIban();
        }
            return iban;

    }

}
