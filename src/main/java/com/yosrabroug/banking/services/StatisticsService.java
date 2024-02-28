package com.yosrabroug.banking.services;

import com.yosrabroug.banking.dto.TransactionSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

    List<TransactionSumDetails> findSumTransactionsByDate(LocalDate startDate, LocalDate EndDate, Integer userId);

    BigDecimal getAccountBalance(Integer userId);
    BigDecimal HighestTransfer(Integer userId);
    BigDecimal HighestDeposit(Integer userId);
}
