package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.dto.Response;
import com.example.bankingapp.database.dto.UserDTO;
import com.example.bankingapp.database.service.AuthService;
import com.example.bankingapp.storage.AppStorage;
import com.example.bankingapp.storage.UserStorage;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SignIn extends BaseActivity {

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
                UserDTO.LogIn user = UserDTO.LogIn
                        .builder()
                        .email(email_input.getEditText().getText().toString())
                        .password(password_input.getEditText().getText().toString())
                        .build();
                login(user);
            }

        });

    }

    private void login(UserDTO.LogIn user) {
        // Create an instance of AuthService
        AuthService authService = Database.getClient().create(AuthService.class);
        Call<Response.LogIn> call = authService.login(user);

        call.enqueue(new Callback<Response.LogIn>() {
            @Override
            public void onResponse(@NonNull Call<Response.LogIn> call, @NonNull retrofit2.Response<Response.LogIn> response) {
                if (response.isSuccessful()) {
                    handleSuccessfulLogin(response.body());
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response.LogIn> call, @NonNull Throwable t) {
                handleFailure(t);
            }
        });
    }

    private void handleSuccessfulLogin(Response.LogIn data) {
        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
        try {
            saveUserData(data);
            navigateToMainActivity();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUserData(Response.LogIn data) throws GeneralSecurityException, IOException {
        assert data != null;
        UserStorage userStorage = new UserStorage(getApplicationContext());

        UserDTO user = data.getUser();
        String token = data.getToken();

        userStorage.saveUser(user);
        userStorage.saveToken(token);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleErrorResponse(retrofit2.Response<Response.LogIn> response) {
        try {
            assert response.errorBody() != null;
            String errorResponse = response.errorBody().string();
            Log.e("TAG", "Error response: " + errorResponse);
            Toast.makeText(getApplicationContext(), errorResponse, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Failed to read error response", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFailure(Throwable t) {
        Toast.makeText(getApplicationContext(), "An error occurred!!!", Toast.LENGTH_SHORT).show();
        Log.e("ERROR", "Error: " + t.getMessage(), t);
    }

}