package com.yosrabroug.banking.services;

import com.yosrabroug.banking.dto.TransactionDto;
import com.yosrabroug.banking.models.Transaction;

import java.util.List;

public interface TransactionService extends AbstractServices<TransactionDto> {

    List<TransactionDto> findAllByUserId(Integer userId);

    // TASK 28/02
    List<TransactionDto> getUserTransactionsByType(String email, String type);
}
