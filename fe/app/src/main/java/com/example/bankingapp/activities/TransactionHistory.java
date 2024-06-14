package com.example.bankingapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.PaymentHistoryAdapter;
import com.example.bankingapp.adapters.TransactionHistoryAdapter;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.models.TransactionHistoryItem;
import com.example.bankingapp.database.models.TransactionHistoryItem;
import com.example.bankingapp.database.service.PaymentSevice;
import com.example.bankingapp.database.service.TrasactionService;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistory extends BaseActivity {
    List<TransactionHistoryItem> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Cấu hình layout manager cho RecyclerView

        setupBackButton();

        // Lấy ra id user hiện tại
        UserStorage userStorage;
        try {
            userStorage = new UserStorage(this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String token = userStorage.getToken();

//        String userId = "123123";

        // Call API
        TrasactionService paymentService = Database.getClient().create(TrasactionService.class);
        Call<List<TransactionHistoryItem>> call = paymentService.getTransitonHistoryList("Bearer "+token);
        Log.e("Token : ",token);
        call.enqueue(new Callback<List<TransactionHistoryItem>>() {

            @Override
            public void onResponse(Call<List<TransactionHistoryItem>> call, Response<List<TransactionHistoryItem>> response) {
                response.message();

                if (response.isSuccessful()) {

                    Log.e("hiihi ", "onResponse: "+ response.body() );
                    assert response.body() != null;
                    itemList.addAll(response.body());
                    recyclerView.setAdapter(new TransactionHistoryAdapter(itemList));
                } else {
                    Log.e("duyvu2612003@gmail.com", "onResponse: " +call.request().header("Authorization"));
                    Toast.makeText(TransactionHistory.this, "Failed to load payment history", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TransactionHistoryItem>> call, Throwable t) {
                Toast.makeText(TransactionHistory.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}