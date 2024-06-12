package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankingapp.R;

public class SuccessTransfer extends AppCompatActivity {

    private Button home, transfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_success_transfer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        home = findViewById(R.id.button);
        transfer = findViewById(R.id.continue_transfer);



        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String amount = intent.getStringExtra("amount");
        TextView textView = findViewById(R.id.textView14);
        textView.setText(String.format("You have successfully transferred $%s to %s", amount, name));

        home.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        });

        transfer.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, Transfer.class);
            startActivity(intent1);

        });


    }
}