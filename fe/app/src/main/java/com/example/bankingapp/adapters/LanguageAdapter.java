package com.example.bankingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.utils.LanguageItem;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private Context context;
    private List<LanguageItem> languageList;
    private OnLanguageSelectedListener listener;
    private String selectedLangCode;
    private int previousSelectedPosition = -1;

    public interface OnLanguageSelectedListener {
        void onLanguageSelected(LanguageItem languageItem) throws GeneralSecurityException, IOException;
    }

    public LanguageAdapter(Context context, List<LanguageItem> languageList, String selectedLangCode, OnLanguageSelectedListener listener) {
        this.context = context;
        this.languageList = languageList;
        this.listener = listener;
        this.selectedLangCode = selectedLangCode;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.language_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        LanguageItem languageItem = languageList.get(position);
        holder.button.setText(languageItem.getName());
        holder.button.setCompoundDrawablesWithIntrinsicBounds(languageItem.getFlagResId(), 0,
                languageItem.getLocaleCode().equals(selectedLangCode) ? R.drawable.baseline_check_24 : 0, 0);

        holder.button.setOnClickListener(v -> {
            if (listener != null) {
                int previousPosition = previousSelectedPosition;
                previousSelectedPosition = holder.getAdapterPosition();
                selectedLangCode = languageItem.getLocaleCode();
                if (previousPosition != -1) {
                    notifyItemChanged(previousPosition);  // Cập nhật mục trước đó
                }
                notifyItemChanged(holder.getAdapterPosition());  // Cập nhật mục mới được chọn
                try {
                    listener.onLanguageSelected(languageItem);
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
