package com.example.bankingapp.activities;

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

public class SignIn extends AppCompatActivity {

    private View focusedInput;
    private TextInputLayout email_input, password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button sign_in_button = findViewById(R.id.submit_button);
        TextView sign_up_link = findViewById(R.id.auth_button);
        email_input = findViewById(R.id.email);
        password_input = findViewById(R.id.password);
        TextView forgot_password_link = findViewById(R.id.forgot_password);

        //redirect signup activity
        sign_up_link.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });

        //redirect forgot_password activity
        forgot_password_link.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForgotPassword.class);
            startActivity(intent);
        });

        //handle input press
        Objects.requireNonNull(email_input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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
                password_input.setError(null);
            }
        });

        //handle sign_in button
        sign_in_button.setOnClickListener(view -> {

            //validate data
            ValidationManager.getInstance().checkEmpty(email_input, password_input);

            //check email and password to sign in
            if (ValidationManager.getInstance().isAllValid()) {
                User user = User
                        .builder()
                        .email(email_input.getEditText().getText().toString())
                        .password(password_input.getEditText().getText().toString())
                        .build();
                login(user);
            }

        });

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            focusedInput = getCurrentFocus();
            if (isFocusedInputClickedOutside(event)) {
                unfocusInput();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isFocusedInputClickedOutside(MotionEvent event) {
        if (focusedInput != null && focusedInput.isFocusableInTouchMode()) {
            Rect outRect = new Rect();
            focusedInput.getGlobalVisibleRect(outRect);
            return !outRect.contains((int) event.getRawX(), (int) event.getRawY());
        }
        return false;
    }

    private void unfocusInput() {
        if (focusedInput != null) {
            focusedInput.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedInput.getWindowToken(), 0);
        }
    }

    private void login(User user) {
        AuthService authService = Database.getClient().create(AuthService.class);
        Call<User> call = authService.login(user);
        call.enqueue(new Callback<User>() {
                         @Override
                         public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                             if (response.isSuccessful()) {
                                 Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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