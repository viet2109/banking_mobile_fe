package com.example.bankingapp.database.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transition implements Serializable {
    private int id;
    private User sender;
    private User receiver;
    private String message;
    private double amount;
    private long time;
    private double fee;
}
