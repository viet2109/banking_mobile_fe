package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.models.BillEntity;
import com.example.bankingapp.database.service.PaymentSevice;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterElectricBillCode extends AppCompatActivity {

    private EditText billCodeEditText;
    private Button checkButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_electricbillcode);

        billCodeEditText = findViewById(R.id.bill_code_edit_text);
        checkButton = findViewById(R.id.check_button);
        backButton = findViewById(R.id.back_button);

        // Initially disable the button
        checkButton.setEnabled(false);

        // Add listener to enable button when text is entered
        billCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButton.setEnabled(s.length() > 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        checkButton.setOnClickListener(v -> {
            // Khi nút kiểm tra được nhấn, gửi bill code vào intent
            // chuyển sang activity PayBillWater.
            try {
                fetchBillInformation(billCodeEditText.getText().toString());
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnClickListener(v -> {
            // Khi nút "back" được nhấn, kết thúc activity hiện tại và quay lại activity trước đó.
            finish();
        });


    }

    private void fetchBillInformation(String billCode) throws GeneralSecurityException, IOException {
        PaymentSevice paymentSevice = Database.getClient().create(PaymentSevice.class);
        String token = new UserStorage(this).getToken();

        Call<BillEntity> call = paymentSevice.getBill(billCode, "Electric", "Bearer " + token);
        Log.e("call", "fetchBillInformation: " + call.request().url());
        call.enqueue(new Callback<BillEntity>() {
            @Override
            public void onResponse(Call<BillEntity> call, Response<BillEntity> response) {
                if (response.body() == null) {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(getApplicationContext(), PayBillElectric.class);
                    intent.putExtra("BILL_CODE", billCodeEditText.getText().toString());
                    startActivity(intent);

                } else {
                    try {
                        Log.e("PayBillElectric", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Failed to fetch bill information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BillEntity> call, Throwable t) {
                Log.e("PayBillElectric", "onFailure: ", t);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
