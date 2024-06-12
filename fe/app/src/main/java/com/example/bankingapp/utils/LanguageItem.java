package com.example.bankingapp.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class LanguageItem {
    private String name;
    private int flagResId;
    private String localeCode;

}

