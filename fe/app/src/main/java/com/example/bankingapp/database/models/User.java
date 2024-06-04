package com.example.bankingapp.database.models;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User implements Serializable {

    private  int id;
    private String name;
    private String email;
    private String password;
    private String cardNumber;
    private String bank;

}
