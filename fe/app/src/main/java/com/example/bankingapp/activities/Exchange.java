package com.example.bankingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class Exchange extends AppCompatActivity {

    private String[] currencies = {"VND", "HK$", "USA", "NT$", "JS"};
    private Spinner fromCurrencySelected;
    private Spinner toCurrencySelected;
    private EditText edtToAmount, edtFromAmount;
    private Button btnExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        fromCurrencySelected = findViewById(R.id.from_currency_spinner);
        toCurrencySelected = findViewById(R.id.to_currency_spinner);
        edtFromAmount = findViewById(R.id.from_amount);
        edtToAmount = findViewById(R.id.to_amount);
        btnExchange = findViewById(R.id.btn_Exchange); // Moved button initialization here

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySelected.setAdapter(adapter);
        toCurrencySelected.setAdapter(adapter);

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

        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ EditText khi Button được nhấn
                int valueIn = Integer.parseInt(edtFromAmount.getText().toString());
                // Xử lý sự kiện khi Button được nhấn
                int valueOut = valueIn/2;
                edtToAmount.setText(valueOut+"");
            }
        });
    }
}
