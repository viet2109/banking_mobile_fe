package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class PayBill extends AppCompatActivity {

    private LinearLayout electric_bill, water_bill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill);

        electric_bill = findViewById(R.id.electric_bill);

        water_bill = findViewById(R.id.water_bill);

        electric_bill.setOnClickListener(v -> {
            Intent intent = new Intent(PayBill.this, EnterElectricBillCode.class);
            startActivity(intent);
        });

        water_bill.setOnClickListener(v -> {
            Intent intent = new Intent(PayBill.this, EnterWaterBillCode.class);
            startActivity(intent);
        });

    }
}
