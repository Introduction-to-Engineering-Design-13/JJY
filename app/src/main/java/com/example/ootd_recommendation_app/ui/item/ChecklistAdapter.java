package com.example.ootd_recommendation_app.ui.item;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ootd_recommendation_app.R;

import java.util.ArrayList;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    private List<ChecklistItem> items;

    public ChecklistAdapter(List<ChecklistItem> items) {
        this.items = items;
    }
    public List<ChecklistItem> getAllItems() {
        return items; // 전체 리스트 반환
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
            textView = view.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public ChecklistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_checklist_item, parent, false);
        return new ViewHolder(view);
    }
    private void removeSubItems(ChecklistItem parentItem) {
        List<ChecklistItem> toRemove = new ArrayList<>();
        for (ChecklistItem child : parentItem.subItems) {
            toRemove.add(child);
            removeSubItems(child); // 재귀적으로 제거
        }
        items.removeAll(toRemove);
    }
    @Override
    public void onBindViewHolder(@NonNull ChecklistAdapter.ViewHolder holder, int position) {
        ChecklistItem item = items.get(position);
        Log.d("ChecklistDebug", "Binding item: " + item.getText());
        holder.textView.setText(item.getText());
        holder.checkBox.setOnCheckedChangeListener(null); // 무한 루프 방지
        holder.checkBox.setChecked(item.isChecked());
        // 들여쓰기 적용
        int paddingStart = 50 * item.getDepth(); // depth 1이면 50dp, 2면 100dp...
        holder.itemView.setPadding(paddingStart, holder.itemView.getPaddingTop(),
                holder.itemView.getPaddingRight(), holder.itemView.getPaddingBottom());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);

            int adapterPosition = holder.getBindingAdapterPosition();
            if (isChecked && item.subItems != null && !item.subItems.isEmpty()) {
                holder.itemView.post(() -> {
                    removeSubItems(item); // 기존 하위항목 먼저 제거

                    List<ChecklistItem> subitems = new ArrayList<>();
                    for (ChecklistItem child : item.subItems) {
                        child.setDepth(item.getDepth() + 1);
                        child.setParent(item);
                        subitems.add(child);
                    }

                    int insertIndex = Math.min(adapterPosition + 1, items.size());
                    items.addAll(insertIndex, subitems);
                    notifyDataSetChanged();
                });
            } else if (!isChecked && item.subItems != null && !item.subItems.isEmpty()) {
                holder.itemView.post(() -> {
                    removeSubItems(item); // 체크 해제 시도 동일한 제거
                    notifyDataSetChanged();
                });
            }
        });


    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
