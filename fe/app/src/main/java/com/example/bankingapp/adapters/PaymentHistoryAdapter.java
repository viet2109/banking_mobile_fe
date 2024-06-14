package com.example.bankingapp.adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.database.models.PaymentHistoryItem;
import com.example.bankingapp.utils.FomatDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentHistoryViewHolder> {

    private List<PaymentHistoryItem> items;

    public PaymentHistoryAdapter(List<PaymentHistoryItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_payment_history, parent, false);
        return new PaymentHistoryViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryViewHolder holder, int position) {
        PaymentHistoryItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMonth;
        private TextView tvStatus;
        private TextView tvAmount;
        private TextView tvDate;

        public PaymentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(PaymentHistoryItem item) {
            if (item==null) return;
            FomatDateTime fomatDateTime = FomatDateTime.builder().time(item.getCreated()).build();
            LocalDateTime localDateTime = fomatDateTime.convertTime();
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Đặt giá trị cho các TextView
            tvMonth.setText(String.valueOf(localDateTime.getMonth()));
            tvStatus.setText(item.getCategory());
            tvAmount.setText(String.valueOf(item.getAmount()));
            tvDate.setText(localDateTime.format(outputFormatter));


        }
    }
}
