package com.example.bankingapp.database.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransitionDTO {
    private String sender;
    private String receiver;
    private double amount;
}
