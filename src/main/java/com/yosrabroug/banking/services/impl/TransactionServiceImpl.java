package com.yosrabroug.banking.services.impl;

import com.yosrabroug.banking.dto.ContactDto;
import com.yosrabroug.banking.dto.TransactionDto;
import com.yosrabroug.banking.models.Transaction;
import com.yosrabroug.banking.models.TransactionType;
import com.yosrabroug.banking.repositories.TransactionRepository;
import com.yosrabroug.banking.services.TransactionService;
import com.yosrabroug.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final ObjectsValidator validator;
    @Override
    public Integer save(TransactionDto dto) {
        validator.validate(dto);
        Transaction transaction = TransactionDto.toEntity(dto);
        BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getType()));
        BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
        transaction.setAmount(amount);
        return repository.save(transaction).getId();
    }

    @Override
    public List<TransactionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(Integer id) {
        return repository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune transaction avec cet id"+id));
    }

    @Override
    public void delete(Integer id) {

        repository.deleteById(id);
    }


    private int getTransactionMultiplier(TransactionType type){
        return TransactionType.TRANSFERT == type ? -1 : 1;
    }

    @Override
    public List<TransactionDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());

    }

    // TASK 28/02
    @Override
    public List<TransactionDto> getUserTransactionsByType(String email, String type) {
        return  repository.findUserTransactionsBytype(email, type)
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }


}
