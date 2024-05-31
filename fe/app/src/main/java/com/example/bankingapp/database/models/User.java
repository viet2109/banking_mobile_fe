package com.example.bankingapp.database.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private  int id;
    private String name;
    private String phone;
    private String email;
    private String password;

}
