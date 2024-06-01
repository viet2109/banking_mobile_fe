package com.example.bankingapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class Exchange extends AppCompatActivity {

//    private String[] currencies = {"VND (Viet Nam Dong)", "HK$ (Hong Kong Dollar)", "USA (Dollar)", "NT$ (Taiwan Dollar)", "JS (Jamaika Dollar)"};
    private String[] currencies = {"VND", "HK$", "USA", "NT$", "JS"};
    private Spinner fromCurrencySelected;
    private Spinner toCurrencySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        fromCurrencySelected = findViewById(R.id.from_currency_spinner);
        toCurrencySelected = findViewById(R.id.to_currency_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.fromCurrencySelected.setAdapter(adapter);
        this.toCurrencySelected.setAdapter(adapter);

        fromCurrencySelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Gọi hàm hiển thị dialog khi một mục được chọn
                fromCurrencySelected.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Đây là nơi bạn có thể xử lý hành động khi không có mục nào được chọn.
            }
        });
        toCurrencySelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Gọi hàm hiển thị dialog khi một mục được chọn
                toCurrencySelected.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Đây là nơi bạn có thể xử lý hành động khi không có mục nào được chọn.
            }
        });
    }
}
