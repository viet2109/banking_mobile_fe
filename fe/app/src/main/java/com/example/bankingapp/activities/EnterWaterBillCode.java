package com.example.bankingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.bankingapp.R;

public class EnterWaterBillCode extends AppCompatActivity {

    private EditText billCodeEditText;
    private Button checkButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_waterbillcode);

        billCodeEditText = findViewById(R.id.bill_code_edit_text);
        checkButton = findViewById(R.id.check_button);
        backButton = findViewById(R.id.back_button);

        // Initially disable the button
        checkButton.setEnabled(false);

        // Add listener to enable button when text is entered
        billCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButton.setEnabled(s.length() > 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        checkButton.setOnClickListener(v -> {
            // Khi nút kiểm tra được nhấn, gửi bill code vào intent
            // chuyển sang activity PayBillWater.

            Intent intent = new Intent(EnterWaterBillCode.this, PayBillWater.class);
            intent.putExtra("BILL_CODE", billCodeEditText.getText());
            startActivity(intent);
        });
        backButton.setOnClickListener(v -> {
            // Khi nút "back" được nhấn, kết thúc activity hiện tại và quay lại activity trước đó.
            finish();
        });


    }
}
