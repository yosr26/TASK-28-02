package com.yosrabroug.banking.controllers;

import com.yosrabroug.banking.dto.ContactDto;
import com.yosrabroug.banking.dto.TransactionDto;
import com.yosrabroug.banking.models.Transaction;
import com.yosrabroug.banking.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;
    private final UserDetailsService userDetailsService;

    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody TransactionDto transactionDto
    ){
        return ResponseEntity.ok(service.save(transactionDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{transaction-id}")
    public ResponseEntity<TransactionDto> findById(
            @PathVariable("transaction-id") Integer transactionId
    ){
        return ResponseEntity.ok(service.findById(transactionId));
    }

    @DeleteMapping("/{transaction-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("transaction-id") Integer transactionId
    ){
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/users/{user-id}")
   public ResponseEntity<List<TransactionDto>> findAllByUserId(
           @PathVariable("user-id") Integer userID
   ){
        return ResponseEntity.ok(service.findAllByUserId(userID));
   }

   // TASK 28/02

   @GetMapping("/transaction-type")
    public ResponseEntity<List<TransactionDto>> getTransactionsByType(
            @RequestParam("transaction-type") String TransactionType,
            Principal principal
   ){
       String userEmail = principal.getName();
       List<TransactionDto> userTransactions =service.getUserTransactionsByType(userEmail, TransactionType);

       return ResponseEntity.ok(userTransactions);

   }

}
