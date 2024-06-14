package com.example.bankingapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

import java.util.HashMap;
import java.util.Map;

public class Exchange extends BaseActivity {

    private String[] currencies = {"VND", "HK$", "USD", "NT$", "JPY"};
    private Spinner fromCurrencySelected;
    private Spinner toCurrencySelected;
    private EditText edtToAmount, edtFromAmount;
    private Button btnExchange;

    // Hardcoded exchange rates (relative to USD)
    private final Map<String, Double> exchangeRates = new HashMap<String, Double>() {{
        put("VND", 0.000043); // 1 VND = 0.000043 USD
        put("HK$", 0.13);     // 1 HK$ = 0.13 USD
        put("USD", 1.0);      // 1 USD = 1 USD
        put("NT$", 0.033);    // 1 NT$ = 0.033 USD
        put("JPY", 0.0073);   // 1 JPY = 0.0073 USD
    }};

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

                double amountIn = Double.parseDouble(fromAmountString);

                // Get the exchange rates
                double fromRate = exchangeRates.getOrDefault(fromCurrency, 1.0);
                double toRate = exchangeRates.getOrDefault(toCurrency, 1.0);

                // Convert to USD first, then to the target currency
                double amountInUSD = amountIn * fromRate;
                double convertedAmount = amountInUSD / toRate;

                edtToAmount.setText(String.valueOf(convertedAmount));
            }
        });
    }
}
