package com.example.bankingapp.database.dto;

import com.example.bankingapp.database.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Response {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class LogIn {
        private String token;
        private UserDTO user;
    }
}
