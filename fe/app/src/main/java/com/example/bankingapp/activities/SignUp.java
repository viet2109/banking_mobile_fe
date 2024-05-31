package com.example.bankingapp.activities;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private View focusedInput;
   private  TextInputLayout email_input, password_input, repeat_password_input;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView header_title = findViewById(R.id.header_title);
        TextView header_description = findViewById(R.id.header_description);
        Button sign_up_button = findViewById(R.id.submit_button);
        TextView sign_in_link = findViewById(R.id.auth_button);
        email_input = findViewById(R.id.email);
        password_input = findViewById(R.id.password);
        repeat_password_input = findViewById(R.id.repeat_password);

        final String[] email_input_value = {""};
        final String[] password_input_value = {""};
        final String[] repeat_password_input_value = {""};

        //Set content texts
        header_title.setText("Create an account");
        header_description.setText("Welcome friend, enter your details so lets get started in ordering food.");
        sign_up_button.setText("Create an account");
        sign_in_link.setText("Login to my account");

        //get all data input
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

            ValidationManager.getInstance().checkEmpty(email_input, password_input, repeat_password_input)
                    .checkEmail(email_input)
                    .checkPassword(password_input)
                    .matchPassword(password_input, repeat_password_input);






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
            return !outRect.contains((int)event.getRawX(), (int)event.getRawY());
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

}