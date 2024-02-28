package com.yosrabroug.banking.services.impl;

import com.yosrabroug.banking.dto.TransactionSumDetails;
import com.yosrabroug.banking.models.TransactionType;
import com.yosrabroug.banking.repositories.TransactionRepository;
import com.yosrabroug.banking.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TransactionRepository transactionRepository;
    @Override
    public List<TransactionSumDetails> findSumTransactionsByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));
        return transactionRepository.findSumTransactionsByDate(start, end, userId);
    }



    @Override
    public BigDecimal getAccountBalance(Integer userId) {
        return transactionRepository.findAccountBalance(userId);
    }

    @Override
    public BigDecimal HighestTransfer(Integer userId) {
        return transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.TRANSFERT);
    }

    @Override
    public BigDecimal HighestDeposit(Integer userId) {
        return transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.DEPOSIT);

    }
}
