package com.example.bankingapp.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if (time == null) {
            return null;
        }


        Pattern pattern1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");
        Pattern pattern2 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}$");


        LocalDateTime dateTime = null;

        Matcher matcher1 = pattern1.matcher(time);
        Matcher matcher2 = pattern2.matcher(time);

        try {
            if (matcher1.matches()) {
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


                dateTime = LocalDateTime.parse(time, formatter1);
            } else if (matcher2.matches()) {
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

                dateTime = LocalDateTime.parse(time, formatter2);
            } else {
                throw new RuntimeException("Invalid date time format: " + time);
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Unable to parse the date time: " + time, e);
        }

        return dateTime;

    }
}
