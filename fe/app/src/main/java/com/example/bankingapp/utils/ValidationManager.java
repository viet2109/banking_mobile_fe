package com.example.bankingapp.utils;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class ValidationManager {
    private static ValidationManager instance = null;
    private static boolean isFirstCheck = true;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*\\d)" +            // at least 1 digit (0-9).
                    "(?=.*[a-z])" +          // at least 1 lowercase letter (A-Z).
                    "(?=.*[A-Z])" +          // at least 1 upper letter (A-Z).
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{8,}" +                // at least 8 characters
                    "$");
    private final String ERR_MSG_CHECK_EMPTY = "Please enter this field.";
    private final String ERR_MSG_CHECK_EMAIL = "Invalid email";
    private final String ERR_MSG_MATCH_PASSWORD = "Password does not match";
    private final String ERR_MSG_CHECK_PASSWORD = "Password must be at least 1 digit, 1 lowercase, 1 uppercase letter, 1 special character, 8 characters, no white spaces";
    private boolean isAllValid = false;
    public ValidationManager() {
    }
    public static ValidationManager getInstance() {
        isFirstCheck = true;
        if (instance==null) {
            instance = new ValidationManager();
        }
        return instance;
    }
    public boolean isAllValid() {
        return isAllValid;
    }
    public  ValidationManager checkEmpty(TextInputLayout... input_layout) {
        int[] isAllMatch = {0};

        clearAllInputErrorWhenFirstCheck(input_layout);

        // check email validate to set input error if input has check fail
        Arrays.stream(input_layout).forEach(input -> {
            String value = Objects.requireNonNull(input.getEditText()).getText().toString().trim();
            if (value.isEmpty()) {
                if (input.getError()==null) {
                    input.setError(ERR_MSG_CHECK_EMPTY);
                }
            } else {
                isAllMatch[0]++;
            }

        });

        isAllValid = (input_layout.length > 0 && isAllMatch[0] == input_layout.length);

        return instance;
    }
    public  ValidationManager checkEmail(TextInputLayout... input_layout) {
        int[] isAllMatch = {0};
        clearAllInputErrorWhenFirstCheck(input_layout);
        Arrays.stream(input_layout).forEach(input -> {
            String value = Objects.requireNonNull(input.getEditText()).getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                if (input.getError()==null) {
                    input.setError(ERR_MSG_CHECK_EMAIL);
                }
            } else {
                isAllMatch[0]++;
            }
        });

        isAllValid = (input_layout.length > 0 && isAllMatch[0] == input_layout.length);

        return instance;
    }
    public ValidationManager checkPassword(TextInputLayout... input_layout) {
       int[] isAllMatch = {0};
       clearAllInputErrorWhenFirstCheck(input_layout);
       Arrays.stream(input_layout).forEach(input -> {
           if (!PASSWORD_PATTERN.matcher(input.getEditText().getText().toString()).matches()) {
               if (input.getError()==null) {
                   input.setError(ERR_MSG_CHECK_PASSWORD);
               } else {
                   isAllMatch[0]++;
               }
           }
       });
        isAllValid = input_layout.length>0 && isAllMatch[0] == input_layout.length;
        return instance;
    }
    public  ValidationManager matchPassword(TextInputLayout password, TextInputLayout re_password) {
        clearAllInputErrorWhenFirstCheck(new TextInputLayout[]{password, re_password});
        if (!password.getEditText().getText().toString().equals(re_password.getEditText().getText().toString())) {
            if (re_password.getError()==null) {
                re_password.setError(ERR_MSG_MATCH_PASSWORD);
                isAllValid = false;
            }
        }
        return instance;
    }
    private void clearAllInputErrorWhenFirstCheck(TextInputLayout[] inputLayout) {
        if (isFirstCheck) {
            Arrays.stream(inputLayout).forEach(input -> {
                input.setError(null);
            });
            isFirstCheck = false;
        }
    }

}
