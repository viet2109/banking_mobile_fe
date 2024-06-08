package com.example.bankingapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.HistoryTransAdapter;
import com.example.bankingapp.database.models.Transition;
import com.example.bankingapp.database.models.User;
import com.example.bankingapp.utils.ValidationManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Transfer extends BaseActivity implements HistoryTransAdapter.OnItemClickListener {

    @Override
    public void onItemClick(User user) {
        Objects.requireNonNull(autoCompleteTextView).setText(user.getBank(), false);
        Objects.requireNonNull(cardNumberInput.getEditText()).setText(user.getCardNumber());

    }

    public enum TransType {
        TRANS_SAME_BANK, TRANS_DIFF_BANK
    }

    private TransType transType;

    private CardView sameBank, diffBank;

    private ArrayList<CardView> transTypeList;

    private String[] bankList = {"Ngân hàng A", "Ngân hàng B", "Ngân hàng C"};

    private TextInputLayout bankInput, cardNumberInput, amountInput, contentInput;
    private AutoCompleteTextView autoCompleteTextView;

    private Button confirmBtn, backBtn;
    private HistoryTransAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transfer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupBackButton();


        autoCompleteTextView = findViewById(R.id.bank);
        sameBank = findViewById(R.id.trans_type_same);
        diffBank = findViewById(R.id.trans_type_diff);
        bankInput = findViewById(R.id.bank_container);
        confirmBtn = findViewById(R.id.confirm_btn);
        cardNumberInput = findViewById(R.id.card_number);
        amountInput = findViewById(R.id.amount);
        contentInput = findViewById(R.id.content);


        transTypeList = new ArrayList<CardView>();

        transTypeList.add(sameBank);
        transTypeList.add(diffBank);

        recyclerView = findViewById(R.id.recyclerView);

        // Tạo danh sách dữ liệu mẫu (sẽ gọi api để lấy dữu liệu)

        User user1 = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .cardNumber("1234567890123456")
                .bank(bankList[0])
                .build();

        User user2 = User.builder()
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .cardNumber("2345678901234567")
                .bank(bankList[1])
                .build();

        User user3 = User.builder()
                .name("Alice Johnson")
                .email("alice.johnson@example.com")
                .cardNumber("3456789012345678")
                .bank(bankList[0])
                .build();

        User user4 = User.builder()
                .name("Bob Brown")
                .email("bob.brown@example.com")
                .cardNumber("4567890123456789")
                .bank(bankList[2])
                .build();

        User user5 = User.builder()
                .name("Charlie Davis")
                .email("charlie.davis@example.com")
                .cardNumber("5678901234567890")
                .bank(bankList[1])
                .build();
        List<User> data = Arrays.asList(new User(), user1, user2, user3, user4, user5);

        // Thiết lập LayoutManager cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Thiết lập Adapter cho RecyclerView
        adapter = new HistoryTransAdapter(this, data, item -> {
        });
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, R.layout.bank_item, bankList));

        setTransType(TransType.TRANS_SAME_BANK);

        sameBank.setOnClickListener(v -> {
            setTransType(TransType.TRANS_SAME_BANK);
        });

        diffBank.setOnClickListener(v -> {
            setTransType(TransType.TRANS_DIFF_BANK);
        });



        confirmBtn.setOnClickListener(v -> {
            //check validate info transfer
            ValidationManager.getInstance().checkEmpty(bankInput, cardNumberInput, amountInput);

            if (!ValidationManager.getInstance().isAllValid()) {
                return;
            }

            // redirect confirm activity
            // actually we need get sender user from sharepreference with key "currentUser"
            User sender = User.builder()
                    .name("viet")
                    .cardNumber("123456789")
                    .build();

            User receiver = User.builder()
                    .name("John")
                    .bank(autoCompleteTextView.getText().toString())
                    .cardNumber(cardNumberInput.getEditText().getText().toString())
                    .build();
            Transition transition = Transition.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(Double.parseDouble(amountInput.getEditText().getText().toString()))
                    .message(contentInput.getEditText().getText().toString())
                    .time(System.currentTimeMillis())
                    .build();

            Intent intent = new Intent(this, ConfirmTransfer.class);
            intent.putExtra("transition", transition);
            startActivity(intent);
        });


    }

    private void setTransType(TransType transType) {
        if (this.transType == transType) return;
        clearInput();
        adapter.reset(recyclerView);
        if (transType == TransType.TRANS_SAME_BANK) {
            this.transType = TransType.TRANS_SAME_BANK;
            activeTypeTrans(sameBank);
            autoCompleteTextView.setText(bankList[0], false);
            bankInput.setVisibility(View.GONE);
        } else if (transType == TransType.TRANS_DIFF_BANK) {
            this.transType = TransType.TRANS_DIFF_BANK;
            activeTypeTrans(diffBank);
            bankInput.setVisibility(View.VISIBLE);
            autoCompleteTextView.setText(null, false);
        }
    }

    private void clearInput() {
        Objects.requireNonNull(cardNumberInput.getEditText()).setText(null);
        Objects.requireNonNull(amountInput.getEditText()).setText(null);
        Objects.requireNonNull(contentInput.getEditText()).setText(null);
    }

    private void activeTypeTrans(CardView cardView) {
        transTypeList.stream()
                .filter(card -> card != cardView && card != null)
                .forEach(card -> {
                    card.setAlpha(0.3F);
                    card.setCardBackgroundColor(getResources().getColor(androidx.cardview.R.color.cardview_dark_background));
                });
        cardView.setCardBackgroundColor(getResources().getColor(R.color.primary));
        cardView.setAlpha(1F);
    }

}