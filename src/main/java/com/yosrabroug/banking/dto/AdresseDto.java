package com.yosrabroug.banking.dto;

import com.yosrabroug.banking.models.Adresse;
import com.yosrabroug.banking.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdresseDto {

    private Integer id;

    private String street;

    private int houseNumber;

    private int zipCode;

    private String city;

    private String Country;

    private Integer userId;

    public static AdresseDto fromEntity(Adresse adresse){
        return AdresseDto.builder()
                .id(adresse.getId())
                .street(adresse.getStreet())
                .houseNumber(adresse.getHouseNumber())
                .zipCode(adresse.getZipCode())
                .city(adresse.getCity())
                .Country(adresse.getCountry())
                .userId(adresse.getUser().getId())
                .build();
    }
    public static Adresse toEntity(AdresseDto adresse){
        return Adresse.builder()
                .Id(adresse.getId())
                .street(adresse.getStreet())
                .houseNumber(adresse.getHouseNumber())
                .zipCode(adresse.getZipCode())
                .city(adresse.getCity())
                .Country(adresse.getCountry())
                .user(
                        User.builder()
                                .Id(adresse.getUserId())
                                .build()
                )
                .build();
    }
}
