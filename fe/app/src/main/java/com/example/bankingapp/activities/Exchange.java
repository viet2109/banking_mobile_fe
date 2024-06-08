package com.example.bankingapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Exchange extends BaseActivity {

    private String[] currencies = {"VND", "HK$", "USA", "NT$", "JS"};
    private Spinner fromCurrencySelected;
    private Spinner toCurrencySelected;
    private EditText edtToAmount, edtFromAmount;
    private Button btnExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        setupBackButton();

        fromCurrencySelected = findViewById(R.id.from_currency_spinner);
        toCurrencySelected = findViewById(R.id.to_currency_spinner);
        edtFromAmount = findViewById(R.id.from_amount);
        edtToAmount = findViewById(R.id.to_amount);
        btnExchange = findViewById(R.id.btn_Exchange);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySelected.setAdapter(adapter);
        toCurrencySelected.setAdapter(adapter);

        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromCurrency = fromCurrencySelected.getSelectedItem().toString();
                String toCurrency = toCurrencySelected.getSelectedItem().toString();
                String fromAmountString = edtFromAmount.getText().toString().trim();

                if (TextUtils.isEmpty(fromAmountString)) {
                    Toast.makeText(getApplicationContext(), "Please enter an amount.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isDigitsOnly(fromAmountString)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid amount.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amountIn = Integer.parseInt(fromAmountString);

                AuthService authService = Database.getClient().create(AuthService.class);
                Call<Integer> call = authService.exchangeCurrency(fromCurrency, toCurrency, amountIn);

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Integer result = response.body();
                            edtToAmount.setText(String.valueOf(result));
                            Toast.makeText(getApplicationContext(), "Exchange successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to exchange currency. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to exchange currency. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
