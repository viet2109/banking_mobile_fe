package com.example.bankingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

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

        //handle sign_in button
        sign_in_button.setOnClickListener(view -> {

            //validate data
            ValidationManager.getInstance().checkEmpty(email_input, password_input);

            //check email and password to sign in
            if (ValidationManager.getInstance().isAllValid()) {

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