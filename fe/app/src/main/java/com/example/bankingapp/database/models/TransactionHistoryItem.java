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
public class TransactionHistoryItem implements Serializable {
    private String id;
    private double amount;
    private double fee;
    private String created;
    private String fromUser;
    private String toUser;
    private String status;


}
