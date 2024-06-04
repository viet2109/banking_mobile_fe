package com.example.bankingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ForgotPassword extends BaseActivity {

    private TextInputLayout email_input;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_input = findViewById(R.id.email_input);
        submit_button = findViewById(R.id.submit_button);

        final String[] email_input_value = {""};

        //get email_input value
        Objects.requireNonNull(email_input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    email_input_value[0] = s.toString().trim();
                    email_input.setError(null);
            }
        });

        submit_button.setOnClickListener(view -> {
            //handle invalid data
            ValidationManager.getInstance().checkEmpty(email_input)
                    .checkEmail(email_input);

            //submit code OTP to email and redirect to reset password activity
            if (ValidationManager.getInstance().isAllValid()) {
                Intent intent = new Intent(this, ResetPassword.class);
                startActivity(intent);
            }


        });


    }





}