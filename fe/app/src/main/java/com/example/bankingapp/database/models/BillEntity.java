package com.example.bankingapp.database.models;


import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillEntity implements Serializable {
    private String code;
    private String userName;
    private String category;
    private Double amount;
    private String phoneNumber;
    private String address;
    private String fromDate;
    private String toDate;
    private Double fee;
    private Double tax;
}
