package com.example.bankingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.dto.TransitionDTO;
import com.example.bankingapp.database.models.Transition;
import com.example.bankingapp.database.service.TransferService;
import com.example.bankingapp.storage.UserStorage;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmTransfer extends BaseActivity {

    private Transition transition;
    private TextInputLayout fromAccountInput, toAccountInput, amountInput, contentInput, bankInput, otpInput, feeInput, cardNumberInput;
    private Button getOtpBtn, confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_transfer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupBackButton();

        fromAccountInput = findViewById(R.id.from);
        toAccountInput = findViewById(R.id.to);
        amountInput = findViewById(R.id.amount);
        contentInput = findViewById(R.id.content);
        feeInput = findViewById(R.id.fee);
        bankInput = findViewById(R.id.bank);
        otpInput = findViewById(R.id.otp);
        cardNumberInput = findViewById(R.id.card_number);
        getOtpBtn = findViewById(R.id.otp_btn);
        confirmBtn = findViewById(R.id.confirm_btn);

        Intent intent = getIntent();
        transition = (Transition) intent.getSerializableExtra("transition");


        assert transition != null;
        Objects.requireNonNull(fromAccountInput.getEditText()).setText(transition.getSender().getCardNumber());
        Objects.requireNonNull(toAccountInput.getEditText()).setText(transition.getReceiver().getName());
        Objects.requireNonNull(cardNumberInput.getEditText()).setText(String.valueOf(transition.getReceiver().getCardNumber()));
        Objects.requireNonNull(amountInput.getEditText()).setText(String.valueOf(transition.getAmount()));
        Objects.requireNonNull(contentInput.getEditText()).setText(transition.getMessage());
        Objects.requireNonNull(bankInput.getEditText()).setText(transition.getReceiver().getBank());
        Objects.requireNonNull(feeInput.getEditText()).setText(String.valueOf(transition.getFee()));
        Objects.requireNonNull(otpInput.getEditText()).setTransformationMethod(null);
        Objects.requireNonNull(otpInput.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmBtn.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getOtpBtn.setOnClickListener(v -> {

            showConfirmationGetOtpDialog();

        });

        confirmBtn.setOnClickListener(v -> {
            //check validate of otp

            //call api to transfer money
            UserStorage userStorage = null;
            try {
                userStorage = new UserStorage(this);
            } catch (Exception ignored) {

            }

            String token = userStorage.getToken();

            TransitionDTO transitionDTO = TransitionDTO.builder()
                    .sender(transition.getSender().getCardNumber())
                    .receiver(transition.getReceiver().getCardNumber())
                    .amount(transition.getAmount())
                    .build();
            TransferService transferService = Database.getClient().create(TransferService.class);
            Call<Object> call = transferService.transfer("Bearer " + token, transitionDTO);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Log.d("ERROR", "Error: " + token);

                    if (response.isSuccessful()) {
                        Intent intent1 = new Intent(getApplicationContext(), SuccessTransfer.class);
                        intent1.putExtra("name", toAccountInput.getEditText().getText().toString());
                        intent1.putExtra("amount", amountInput.getEditText().getText().toString());
                        startActivity(intent1);
                    } else {
                        // Handle error response
                        assert response.errorBody() != null;
                        try {
                            Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error occurred!!!", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "Error: " + t.getMessage(), t);
                }
            });


        });

    }

    private void showConfirmationGetOtpDialog() {

        // Tạo một AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Thiết lập tiêu đề và nội dung cho hộp thoại
        builder.setTitle("Xác nhận gửi OTP đến email của bạn");
        builder.setMessage("Chúng tôi sẽ gửi một mã OTP đến email " + transition.getSender().getEmail());

        // Thiết lập nút Yes và hành động khi người dùng nhấn Yes
        builder.setPositiveButton("Gửi mã", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hành động khi người dùng nhấn Yes
                // gọi api để gửi otp đến email của người nhận
                performAction();
            }
        });

        // Thiết lập nút No và hành động khi người dùng nhấn No
        builder.setNegativeButton("Tôi cần kiểm tra lại thông tin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hành động khi người dùng nhấn No
                // Ví dụ: đóng hộp thoại
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị hộp thoại
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performAction() {
        //gọi api

        //trả về kết quả

    }
}