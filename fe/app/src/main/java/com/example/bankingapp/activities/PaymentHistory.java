package com.example.bankingapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.PaymentHistoryAdapter;
import com.example.bankingapp.database.models.PaymentHistoryItem;

import java.util.Arrays;
import java.util.List;

public class PaymentHistory extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setupBackButton();


        // Giả sử chúng ta có userId là "user1"
        String userId = "user1";

        List<PaymentHistoryItem> paymentHistoryItems = Arrays.asList(
                new PaymentHistoryItem(userId, "October", "Unsuccessfully", "$480", "30/10/2019"),
                new PaymentHistoryItem(userId, "September", "Successfully", "$480", "30/09/2019"),
                new PaymentHistoryItem(userId, "August", "Successfully", "$480", "30/08/2019"),
                new PaymentHistoryItem(userId, "July", "Successfully", "$480", "30/07/2019"),
                new PaymentHistoryItem(userId, "June", "Successfully", "$480", "30/06/2019"),
                new PaymentHistoryItem(userId, "May", "Successfully", "$480", "30/05/2019")
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PaymentHistoryAdapter(paymentHistoryItems));
    }
}