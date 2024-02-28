package com.yosrabroug.banking.repositories;

import com.yosrabroug.banking.dto.TransactionDto;
import com.yosrabroug.banking.dto.TransactionSumDetails;
import com.yosrabroug.banking.models.*;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    List<Transaction> findAllByUserId(Integer userId);

    @Query("select sum(t.amount) from Transaction t where t.user.Id = :userId")
    BigDecimal findAccountBalance(@Param("userId") Integer userId);


    @Query("select max(abs(t.amount)) from Transaction t where t.user.Id = :userId and t.type= :transactionType")
    BigDecimal findHighestAmountByTransactionType(Integer userId, TransactionType transactionType);

    // quand on utilise une méthode de somme avec d'autres attributs on utilise group by
    @Query("select t.transactionDate as transactionDate, sum(t.amount) as amount from Transaction t where (t.createdDate between :start and :end) and (t.user.Id = :userId) group by t.transactionDate")
    List<TransactionSumDetails> findSumTransactionsByDate(LocalDateTime start, LocalDateTime end, Integer userId);


    // TASK 28/02
    @Query("select t from Transaction t where t.type= :type and t.user.email = :email")
    List<Transaction> findUserTransactionsBytype(String email, String type);
}
