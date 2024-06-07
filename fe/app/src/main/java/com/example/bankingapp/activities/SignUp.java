package com.example.bankingapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.models.User;
import com.example.bankingapp.database.service.AuthService;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends BaseActivity {
    private TextInputLayout name_input, email_input, password_input, repeat_password_input;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView header_title = findViewById(R.id.header_title);
        TextView header_description = findViewById(R.id.header_description);
        Button sign_up_button = findViewById(R.id.submit_button);
        TextView sign_in_link = findViewById(R.id.auth_button);

        name_input = findViewById(R.id.name);
        email_input = findViewById(R.id.email);
        password_input = findViewById(R.id.password);
        repeat_password_input = findViewById(R.id.repeat_password);

        final String[] name_input_value = {""};
        final String[] email_input_value = {""};
        final String[] password_input_value = {""};
        final String[] repeat_password_input_value = {""};

        //Set content texts
        header_title.setText("Create an account");
        header_description.setText("Welcome friend, enter your details to continue.");
        sign_up_button.setText("Create an account");
        sign_in_link.setText("Login to my account");

        //get all data input

        Objects.requireNonNull(name_input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name_input.setError(null);
            }
        });
        Objects.requireNonNull(email_input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email_input_value[0] = s.toString();
                email_input.setError(null);
            }
        });
        Objects.requireNonNull(password_input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password_input_value[0] = s.toString();
                password_input.setError(null);
            }
        });
        Objects.requireNonNull(repeat_password_input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repeat_password_input_value[0] = s.toString();
                repeat_password_input.setError(null);
            }
        });

        //redirect sign_in activity
        sign_in_link.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        });

        //hanlde sign_up button
        sign_up_button.setOnClickListener(view -> {

            ValidationManager.getInstance().checkEmpty(email_input, password_input, repeat_password_input, name_input)
                    .checkEmail(email_input)
                    .checkPassword(password_input)
                    .matchPassword(password_input, repeat_password_input);

            if (ValidationManager.getInstance().isAllValid()) {
                Log.d("TAG", "onCreate");
                User user = User
                        .builder()
                        .name(name_input.getEditText().getText().toString())
                        .email(email_input.getEditText().getText().toString())
                        .password(password_input.getEditText().getText().toString())
                        .build();
                register(user);
            } else {
                Log.d("TAG", "failed");

            }


        });


    }

    private void register(User user) {
        Log.d("Register", user.toString());
        AuthService authService = Database.getClient().create(AuthService.class);
        Call<User> call = authService.register(user);
        Log.d("TAG", "register");
        call.enqueue(new Callback<User>() {
                         @Override
                         public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                             if (response.isSuccessful()) {
                                 Toast.makeText(getApplicationContext(), "Register successful!", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                 startActivity(intent);
                             } else {
                                 try {
                                     assert response.errorBody() != null;
                                     String errorResponse = response.errorBody().string();
                                     Log.e("TAG", "Error response: " + errorResponse);
                                     Toast.makeText(getApplicationContext(), errorResponse, Toast.LENGTH_SHORT).show();

                                 } catch (IOException e) {
                                     e.printStackTrace();
                                     Toast.makeText(getApplicationContext(), "Failed to read error response", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                             Toast.makeText(getApplicationContext(), "An error occurred!!!", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
    }

}