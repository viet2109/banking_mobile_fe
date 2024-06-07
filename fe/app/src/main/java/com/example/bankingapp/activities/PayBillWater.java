package com.example.bankingapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class PayBillWater extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill_water);
        setupBackButton();
        EditText otpInput = findViewById(R.id.otp_input);
        Button getOtpButton = findViewById(R.id.get_otp_button);
        Button payButton = findViewById(R.id.pay_button);

        getOtpButton.setOnClickListener(view -> {
            // Logic lấy OTP
            otpInput.setText("123456"); // OTP giả để thử nghiệm
        });

        otpInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String otp = otpInput.getText().toString();
                payButton.setEnabled(otp.length() == 6);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        payButton.setOnClickListener(view -> {
            // Logic để thanh toán hóa đơn
        });
    }
}
