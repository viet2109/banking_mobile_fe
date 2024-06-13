package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.models.BillEntity;
import com.example.bankingapp.database.service.AuthService;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayBillElectric extends BaseActivity {
    private EditText otpInput;
    private Button getOtpButton;
    private Button payButton;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView codeTextView;
    private TextView fromDateTextView;
    private TextView toDateTextView;
    private TextView electricFeeTextView;
    private TextView taxTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill_electric);
        setupBackButton();
        otpInput = findViewById(R.id.otpElectric_input);
        getOtpButton = findViewById(R.id.get_otp_button);
        payButton = findViewById(R.id.pay_button);
        nameTextView = findViewById(R.id.name);
        addressTextView = findViewById(R.id.address);
        phoneTextView = findViewById(R.id.phone);
        codeTextView = findViewById(R.id.code);
        fromDateTextView = findViewById(R.id.from_date);
        toDateTextView = findViewById(R.id.to_date);
        electricFeeTextView = findViewById(R.id.water_fee);
        taxTextView = findViewById(R.id.tax);
        totalTextView = findViewById(R.id.total);

        // Gọi API để lấy thông tin hóa đơn
        String billCode = getIntent().getStringExtra("BILL_CODE"); // Lấy mã hóa đơn từ Intent
        fetchBillInformation(billCode);

        getOtpButton.setOnClickListener(view -> {
            // Logic lấy OTP - hỏi việt chỗ này
            otpInput.setText("123456"); // OTP giả để thử nghiệm
        });

        otpInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String otp = otpInput.getText().toString();
                payButton.setEnabled(otp.length() == 6);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        // nhấn nút thanh toán
        payButton.setOnClickListener(view -> {
            // call api thanh toán hóa đơn
            AuthService authService = Database.getClient().create(AuthService.class);
            Call<Object> call = authService.payBill(billCode);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    // thành công thì chuyển sang activity pay Success
                    if (response.isSuccessful()){
                        Toast.makeText(PayBillElectric.this, "Pay bill electric success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayBillElectric.this, PayBillSuccess.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(PayBillElectric.this, "Pay bill electric fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(PayBillElectric.this, "Pay bill water fail", Toast.LENGTH_SHORT).show();
                }
            });


        });
    }

    private void fetchBillInformation(String billCode) {
        AuthService authService = Database.getClient().create(AuthService.class);
        Call<BillEntity> call = authService.getBill(billCode);
        call.enqueue(new Callback<BillEntity>() {
            @Override
            public void onResponse(Call<BillEntity> call, Response<BillEntity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BillEntity bill = response.body();
                    // Hiển thị thông tin hóa đơn lên giao diện
                    nameTextView.setText("Name: " + bill.getUserName());
                    addressTextView.setText("Address: " + bill.getAddress());
                    phoneTextView.setText("Phone number: " + bill.getPhoneNumber());
                    codeTextView.setText("Code: " + bill.getCode());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    fromDateTextView.setText("From: " + dateFormat.format(bill.getFromDate()));
                    toDateTextView.setText("To: " + dateFormat.format(bill.getToDate()));
                    electricFeeTextView.setText("Electric fee: " + bill.getFee());
                    taxTextView.setText("Tax: " + bill.getTax());
                    double total = bill.getFee() + bill.getTax();
                    totalTextView.setText("TOTAL: " + total);
                } else {
                    Toast.makeText(PayBillElectric.this, "Failed to fetch bill information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BillEntity> call, Throwable t) {
                Toast.makeText(PayBillElectric.this, "Failed to fetch bill information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
