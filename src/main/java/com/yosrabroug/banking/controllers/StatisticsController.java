package com.yosrabroug.banking.controllers;

import com.yosrabroug.banking.dto.TransactionSumDetails;
import com.yosrabroug.banking.services.StatisticsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping("/sum-by-date/{user-id}")
    public ResponseEntity<List<TransactionSumDetails>> findSumTransactionsByDate(
            @RequestParam("start-date") LocalDate startDate,
            @RequestParam("end-date") @DateTimeFormat(pattern ="yyyy-MM-dd" ) LocalDate EndDate,
            @PathVariable("user-id") @DateTimeFormat(pattern ="yyyy-MM-dd" ) Integer userId){
        return ResponseEntity.ok(service.findSumTransactionsByDate(startDate, EndDate, userId));
    }

    @GetMapping("/account-balance/{user-id}")
    public ResponseEntity<BigDecimal> getAccountBalance(
            @PathVariable("user-id") Integer userId){
            return ResponseEntity.ok(service.getAccountBalance(userId));
    }
    @GetMapping("/highest-transfer/{user-id}")
    public ResponseEntity<BigDecimal> HighestTransfer(
            @PathVariable("user-id") Integer userId){
        return ResponseEntity.ok(service.HighestTransfer(userId));
    }
    @GetMapping("/highest-deposit/{user-id}")
    public ResponseEntity< BigDecimal> HighestDeposit(
            @PathVariable("user-id") Integer userId){
        return ResponseEntity.ok(service.HighestDeposit(userId));
    }
}
