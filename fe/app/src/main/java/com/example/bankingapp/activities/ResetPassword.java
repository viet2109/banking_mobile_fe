package com.example.bankingapp.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class ResetPassword extends AppCompatActivity {

    private View focusedInput;
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