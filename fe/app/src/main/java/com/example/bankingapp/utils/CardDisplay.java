package com.example.bankingapp.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardDisplay {
    private String cardNumber;

    public String display() {
        // Kiểm tra độ dài của số thẻ phải ít nhất là 6
        if (cardNumber == null || cardNumber.length() < 6) {
            throw new IllegalArgumentException("Số thẻ phải có ít nhất 6 ký tự.");
        }

        // Lấy 3 ký tự đầu
        String firstThree = cardNumber.substring(0, 3);

        // Lấy 3 ký tự cuối
        String lastThree = cardNumber.substring(cardNumber.length() - 3);

        // Tính toán số lượng ký tự ẩn
        int numHiddenChars = cardNumber.length() - 6;
        StringBuilder hidden = new StringBuilder();
        for (int i = 0; i < numHiddenChars; i++) {
            if (i > 0 && i % 3 == 0) {
                hidden.append(' ');
            }
            hidden.append('*');
        }

        // Kết hợp các phần
        return firstThree + " " + hidden.toString() + " " + lastThree;
    }
}
