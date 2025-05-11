package com.example.ootd_recommendation_app.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ootd_recommendation_app.R;
import java.util.List;

public class CheckedItemAdapter extends RecyclerView.Adapter<CheckedItemAdapter.ViewHolder> {

    private final List<String> checkedItems;

    public CheckedItemAdapter(List<String> checkedItems) {
        this.checkedItems = checkedItems;
    }
    public void updateData(List<String> newItems) {
        checkedItems.clear();
        checkedItems.addAll(newItems);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checked_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemText = checkedItems.get(position);
        holder.textView.setText(itemText);

        // ❗ 향후 삭제 버튼 또는 색상 강조 가능성 고려
    }

    @Override
    public int getItemCount() {
        return checkedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.checked_item_text);
        }
    }
}