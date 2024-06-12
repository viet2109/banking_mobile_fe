package com.example.bankingapp.database.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class UserDTO implements Serializable {

    private String name;
    private String email;
    private String cardNumber;
    private String bank;
    private double balance;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class LogIn {
        private String email;
        private String password;

        public UserDTO convertUserDTO(UserDTO userDTO) {
            return UserDTO.builder()
                    .name(userDTO.getName())
                    .email(userDTO.getEmail()).build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Register {
        private String name;
        private String email;
        private String password;
    }

}
