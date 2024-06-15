package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.service.AuthService;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                AuthService authService = Database.getClient().create(AuthService.class);
                Call<Void> call = authService.forgotPassword(email_input_value[0]);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "We has already sent a pass to your email", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);
                        } else {
                            try {
                                Toast.makeText(ForgotPassword.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });


    }


}