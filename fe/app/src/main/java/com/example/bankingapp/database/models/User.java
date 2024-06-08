package com.example.bankingapp.database.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private  int id;
    private String name;
    private String email;
    private String password;
    private String cardNumber;
    private double balance;
    private String bank;

}
