package com.example.bankingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bankingapp.R;
import com.example.bankingapp.database.models.User;
import com.google.gson.Gson;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class HistoryTransAdapter extends RecyclerView.Adapter<HistoryTransAdapter.ViewHolder> {

    private Context context;
    private List<User> data;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition = -1; // Position of the selected item

    public HistoryTransAdapter(Context context, List<User> data, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.account_history_img);
            textView = itemView.findViewById(R.id.account_history_name);
            cardView = itemView.findViewById(R.id.card_history);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User item);
    }

    @NonNull
    @Override
    public HistoryTransAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_transfer_account_item, parent, false);
        return new ViewHolder(view);
    }

    public void reset(RecyclerView recyclerView) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = -1; // Đặt selectedPosition về giá trị mặc định

        // Thông báo cho Adapter về sự thay đổi cho item trước đó được chọn
        notifyItemChanged(previousSelectedPosition);

        // Nếu bạn muốn thay đổi CardView của item trước đó được chọn
        ViewHolder previousViewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(previousSelectedPosition);
        if (previousViewHolder != null) {
            previousViewHolder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            previousViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.text_black_bold));
            // Thực hiện bất kỳ thay đổi khác tại đây nếu cần
        }

    }
    @Override
    public void onBindViewHolder(@NonNull HistoryTransAdapter.ViewHolder holder, int position) {
        User user = data.get(position);

        if (position == 0) {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_add_circle_24));
            holder.textView.setVisibility(View.INVISIBLE);
        } else {
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(user.getName());
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.avatar));

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(user);
                }

                int previousSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition(); // Get the current position of the item

                // Notify the adapter about the changes
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);
            });

            // Set background color and text color based on the selected position
            if (selectedPosition == position) {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary));
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            } else {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.text_black_bold));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
