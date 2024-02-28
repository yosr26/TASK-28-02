package com.yosrabroug.banking.dto;

import com.yosrabroug.banking.models.Transaction;
import com.yosrabroug.banking.models.TransactionType;
import com.yosrabroug.banking.models.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Integer id;

    @Positive
    @Max(value = 1000000000)
    private BigDecimal amount;

    private String destinationIban;

    private TransactionType type;

    private Integer userId;

    private LocalDate transactionDate;

    public static TransactionDto fromEntity(Transaction transaction){
        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transactionDate(transaction.getTransactionDate())
                .destinationIban(transaction.getDestinationIban())
                .userId(transaction.getUser().getId())
                .build();
    }
    public static Transaction toEntity(TransactionDto transaction){
        return Transaction.builder()
                .Id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transactionDate(LocalDate.now())
                .destinationIban(transaction.getDestinationIban())
                .user(
                        User.builder()
                                .Id(transaction.getUserId())
                                .build()
                )
                .build();
    }
}
