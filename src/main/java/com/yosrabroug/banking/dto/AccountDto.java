package com.yosrabroug.banking.dto;

import com.yosrabroug.banking.models.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AccountDto {

    private Integer id;

    private String iban;

    private UserDto user;

    public static AccountDto fromEntity(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .iban(account.getIban())
                .user(UserDto.fromEntity(account.getUser()))
                .build();
    }
    public static Account toEntity(AccountDto account){
        return Account.builder()
                .Id(account.getId())
                .iban(account.getIban())
                .user(UserDto.toEntity(account.getUser()))
                .build();
    }
}
