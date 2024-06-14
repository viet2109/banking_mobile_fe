package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class PayBillSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill_success);

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiển thị thông báo chuyển về trang Pay bill
                Toast.makeText(PayBillSuccess.this, "Confirmed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PayBillSuccess.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}