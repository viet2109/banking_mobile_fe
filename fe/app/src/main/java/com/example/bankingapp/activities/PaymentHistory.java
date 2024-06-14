package com.example.bankingapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.PaymentHistoryAdapter;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.models.PaymentHistoryItem;
import com.example.bankingapp.database.service.AuthService;
import com.example.bankingapp.database.service.PaymentSevice;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHistory extends BaseActivity {

    List<PaymentHistoryItem> itemList = new ArrayList<>();

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
        PaymentSevice paymentService = Database.getClient().create(PaymentSevice.class);
        Call<List<PaymentHistoryItem>> call = paymentService.getPaymentByCate("Electric", "Bearer "+token);
        Log.e("Token : ",token);
        call.enqueue(new Callback<List<PaymentHistoryItem>>() {

            @Override
            public void onResponse(Call<List<PaymentHistoryItem>> call, Response<List<PaymentHistoryItem>> response) {
              response.message();

                if (response.isSuccessful()) {

                    Log.e("hiihi ", "onResponse: "+ response.body() );
                    assert response.body() != null;
                    itemList.addAll(response.body());
                    recyclerView.setAdapter(new PaymentHistoryAdapter(itemList));
                } else {
                    Log.e("duyvu2612003@gmail.com", "onResponse: " +call.request().header("Authorization"));
                    Toast.makeText(PaymentHistory.this, "Failed to load payment history", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PaymentHistoryItem>> call, Throwable t) {
                Toast.makeText(PaymentHistory.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
