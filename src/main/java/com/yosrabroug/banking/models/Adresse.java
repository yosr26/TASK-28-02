package com.yosrabroug.banking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Adresse extends AbstractEntity{


    private String street;

    private int houseNumber;

    private int zipCode;

    private String city;

    private String Country;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

}
