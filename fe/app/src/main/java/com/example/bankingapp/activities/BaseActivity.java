package com.example.bankingapp.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class BaseActivity extends AppCompatActivity {

    private View focusedInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void setupBackButton() {
        // Lấy nút "Back" bằng ID (assume tất cả Activity có nút back với id là "button_back")

        Button buttonBack = findViewById(R.id.back_button);

        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> {
                // Xử lý sự kiện nút back
                finish();
            });
        }
    }
}

