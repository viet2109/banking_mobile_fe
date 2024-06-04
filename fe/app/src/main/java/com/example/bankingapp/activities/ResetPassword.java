package com.example.bankingapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.bankingapp.R;

public class ResetPassword extends BaseActivity {

    private EditText code_input_1, code_input_2, code_input_3, code_input_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        code_input_1 = findViewById(R.id.code_input_1);
        code_input_2 = findViewById(R.id.code_input_2);
        code_input_3 = findViewById(R.id.code_input_3);
        code_input_4 = findViewById(R.id.code_input_4);

        setUpOTPInputs();
    }

    private void setUpOTPInputs() {
        // Show content code
        code_input_1.setTransformationMethod(null);
        code_input_2.setTransformationMethod(null);
        code_input_3.setTransformationMethod(null);
        code_input_4.setTransformationMethod(null);

        //auto change next code input
        code_input_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) code_input_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code_input_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) code_input_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code_input_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) code_input_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}