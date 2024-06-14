package com.example.bankingapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.models.BillEntity;
import com.example.bankingapp.database.service.AuthService;
import com.example.bankingapp.database.service.PaymentSevice;
import com.example.bankingapp.storage.UserStorage;
import com.example.bankingapp.utils.FomatDateTime;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayBillWater extends BaseActivity {
    private EditText otpInput;
    private Button getOtpButton;
    private Button payButton;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView codeTextView;
    private TextView fromDateTextView;
    private TextView toDateTextView;
    private TextView waterFeeTextView;
    private TextView taxTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill_water);
        setupBackButton();
        otpInput = findViewById(R.id.otp_input);
        getOtpButton = findViewById(R.id.get_otp_button);
        payButton = findViewById(R.id.pay_button);

        nameTextView = findViewById(R.id.textView4);
        addressTextView = findViewById(R.id.textView5);
        phoneTextView = findViewById(R.id.textView6);
        codeTextView = findViewById(R.id.textView7);
        fromDateTextView = findViewById(R.id.textView8);
        toDateTextView = findViewById(R.id.textView9);
        waterFeeTextView = findViewById(R.id.textView11);
        taxTextView = findViewById(R.id.textView12);
        totalTextView = findViewById(R.id.textView13);


        String billCode = getIntent().getStringExtra("BILL_CODE"); // Lấy mã hóa đơn từ Intent
        Log.e("billcode", "onCreate: "+ billCode );
        try {
            // gọi api lấy thông tin hóa đơn
            fetchBillInformation(billCode);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getOtpButton.setOnClickListener(view -> {
            // Logic lấy OTP
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
            String token;
            try {
                token = new UserStorage(this).getToken();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PaymentSevice paymentSevice = Database.getClient().create(PaymentSevice.class);
            Call<Object> call = paymentSevice.payBill(billCode,"Bearer "+token);


            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                    // thành công thì chuyển sang activity pay Success
                    if (response.isSuccessful()){
                        Toast.makeText(PayBillWater.this, "Pay bill water success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayBillWater.this, PayBillSuccess.class);
                        startActivity(intent);

                    }else{
                        try {
                            Log.e("PayBillWater", "Error: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(PayBillWater.this, "Pay bill water fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                    Toast.makeText(PayBillWater.this, "Pay bill water success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PayBillWater.this, PayBillSuccess.class);
                    startActivity(intent);
                }
            });
        });
    }


    private void fetchBillInformation(String billCode) throws GeneralSecurityException, IOException {
        PaymentSevice paymentSevice = Database.getClient().create(PaymentSevice.class);
        String token = new UserStorage(this).getToken();
        Call<BillEntity> call = paymentSevice.getBill(billCode, "Bearer "+ token);
        Log.e("call", "fetchBillInformation: " +call.request().url() );
        call.enqueue(new Callback<BillEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<BillEntity> call, Response<BillEntity> response) {
                Log.e("response", "onResponse: " + response.body());
                if (response.body() == null) {
                    try {
                        Toast.makeText(PayBillWater.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    finish();
                    return;
                }
                if (response.isSuccessful() && response.body() != null) {
                    BillEntity bill = response.body();
                    // Hiển thị thông tin hóa đơn lên giao diện
                    FomatDateTime fomatDateTime = new FomatDateTime(bill.getFromDate());
                    FomatDateTime fomatDateTime1 = new FomatDateTime(bill.getToDate());

                    LocalDateTime fromDateTime = fomatDateTime.convertTime();
                    LocalDateTime toDateTime = fomatDateTime1.convertTime();

                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    nameTextView.setText(bill.getUserName());
                    addressTextView.setText(bill.getAddress());
                    phoneTextView.setText(bill.getPhoneNumber());
                    codeTextView.setText(bill.getCode());
                    fromDateTextView.setText(dateFormat.format(fromDateTime));
                    toDateTextView.setText(dateFormat.format(toDateTime));
                    waterFeeTextView.setText(bill.getFee()+"");
                    taxTextView.setText(bill.getTax()+"");
                    double total = bill.getFee() + bill.getTax();
                    totalTextView.setText(total+"");
                } else {
                    try {
                        Log.e("PayBillWater", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(PayBillWater.this, "Failed to fetch bill information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BillEntity> call, Throwable t) {
                Log.e("PayBillEle", "onFailure: ", t);
                Toast.makeText(PayBillWater.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
