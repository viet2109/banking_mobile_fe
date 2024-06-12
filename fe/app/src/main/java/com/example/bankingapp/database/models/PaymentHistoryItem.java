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
public class PaymentHistoryItem implements Serializable {
    private String paymentId;
    private double amount;
    private String category;
    private LocalDateTime createAt;
    private String forUser;
    private String status;
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public void setForUser(String forUser) {
        this.forUser = forUser;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getForUser() {
        return forUser;
    }

    public String getStatus() {
        return status;
    }

}
