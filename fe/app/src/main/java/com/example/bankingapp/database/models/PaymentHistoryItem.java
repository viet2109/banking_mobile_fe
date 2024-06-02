package com.example.bankingapp.database.models;

public class PaymentHistoryItem {
    private String userId;
    private String month;
    private String status;
    private String amount;
    private String date;

    public PaymentHistoryItem(String userId, String month, String status, String amount, String date) {
        this.userId = userId;
        this.month = month;
        this.status = status;
        this.amount = amount;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public String getMonth() {
        return month;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

