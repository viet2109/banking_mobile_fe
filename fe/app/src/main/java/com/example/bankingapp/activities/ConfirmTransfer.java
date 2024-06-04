package com.example.bankingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bankingapp.R;
import com.example.bankingapp.database.models.Transition;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ConfirmTransfer extends BaseActivity {

    private Transition transition;
    private TextInputLayout fromAccountInput, toAccountInput, amountInput, contentInput, bankInput, otpInput, feeInput, cardNumberInput;
    private Button getOtpBtn;
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

        getOtpBtn.setOnClickListener(v -> {

            showConfirmationGetOtpDialog();

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
                // Ví dụ: thực hiện hành động cần thiết
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
        // Thực hiện hành động cần thiết khi người dùng xác nhận
        // Ví dụ: Xóa một mục, gửi dữ liệu, v.v.
    }
}