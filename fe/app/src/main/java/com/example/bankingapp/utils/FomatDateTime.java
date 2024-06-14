package com.example.bankingapp.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FomatDateTime {
    private String time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime convertTime() {
        try {
            if (time == null) {
                System.out.println("Chuỗi thời gian đầu vào là null");
                return null;
            }

            // Định nghĩa định dạng của chuỗi đầu vào
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            // Chuyển đổi chuỗi thành LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);



            // In ra để kiểm tra
            return dateTime;
        } catch (DateTimeParseException e) {
            // Định nghĩa định dạng của chuỗi đầu vào
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

            // Chuyển đổi chuỗi thành LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);

            // In ra để kiểm tra
            return dateTime;
        } catch (Exception e) {
            System.out.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
