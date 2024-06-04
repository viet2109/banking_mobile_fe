package com.example.bankingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bankingapp.R;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class HistoryTransAdapter extends RecyclerView.Adapter<HistoryTransAdapter.ViewHolder> {

    private Context context;
    private List<Integer> data; // Thay đổi kiểu dữ liệu tùy thuộc vào dữ liệu bạn muốn hiển thị
    private OnItemClickListener onItemClickListener;



    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // Thay đổi kiểu dữ liệu tùy thuộc vào giao diện người dùng bạn muốn hiển thị
        TextView textView;

        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.account_history_img); // Thay R.id.textView bằng ID của TextView trong item layout
            textView = itemView.findViewById(R.id.account_history_name);
            cardView = itemView.findViewById(R.id.card_history);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    @NonNull
    @Override
    public HistoryTransAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_transfer_account_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTransAdapter.ViewHolder holder, int position) {
        int item = data.get(position);

        if (position == 0) {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_add_circle_24));
            holder.textView.setVisibility(View.INVISIBLE);

        } else {
            holder.textView.setText("Viet");
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.avatar));
            holder.itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(item);
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
