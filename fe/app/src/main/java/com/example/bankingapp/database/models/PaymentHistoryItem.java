package com.example.bankingapp.database.models;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentHistoryItem implements Serializable {
    private String id;
    private double amount;
    private String category;
    private String created;
    private String forUser;
    private String status;


}
