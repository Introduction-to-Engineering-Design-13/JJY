package com.example.ootd_recommendation_app.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ootd_recommendation_app.R;

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

    @Override
    public void onBindViewHolder(@NonNull ChecklistAdapter.ViewHolder holder, int position) {
        ChecklistItem item = items.get(position);
        holder.textView.setText(item.getText());
        holder.checkBox.setOnCheckedChangeListener(null); // 무한 루프 방지
        holder.checkBox.setChecked(item.isChecked());
        // 들여쓰기 적용
        int paddingStart = 50 * item.getDepth(); // depth 1이면 40dp, 2면 80dp...
        holder.itemView.setPadding(paddingStart, holder.itemView.getPaddingTop(),
                holder.itemView.getPaddingRight(), holder.itemView.getPaddingBottom());

     /*   // 체크 이벤트
        holder.checkBox.setOnCheckedChangeListener(null); // 리스너 초기화
        holder.checkBox.setChecked(item.isChecked());
*/
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            // 하위 항목 확장
            if (isChecked && item.subItems != null && !item.subItems.isEmpty()) {
                int adapterPosition = holder.getBindingAdapterPosition();
                holder.itemView.post(() -> {
                    for (int i = 0; i < item.subItems.size(); i++) {
                        ChecklistItem child = item.subItems.get(i);
                        child.setDepth(item.getDepth() + 1);
                        items.add(position + i + 1, child);
                    }
                    notifyItemRangeInserted(position + 1, item.subItems.size());
                });
            }

            // 체크 해제 시 하위 항목 제거
            else if (!isChecked && item.subItems != null && !item.subItems.isEmpty()) {
                int adapterPosition = holder.getBindingAdapterPosition();
                holder.itemView.post(() -> {
                    int removedCount = 0;
                    while (position + 1 < items.size() && items.get(position + 1).getParent() == item) {
                        items.remove(position + 1);
                        removedCount++;
                    }
                    if (removedCount > 0) {
                        notifyItemRangeRemoved(position + 1, removedCount);
                    }
                });
            }
        });
            /* if (isChecked && item.subItems != null && !item.subItems.isEmpty()) {
                for (ChecklistItem sub : item.subItems) {
                    sub.setChecked(isChecked); // 부모와 같은 체크 상태로 설정
                    sub.setDepth(item.getDepth() + 1);
                }
                items.addAll(position + 1, item.subItems);
                notifyItemRangeInserted(position + 1, item.subItems.size());
            }

            else if (!isChecked && item.subItems != null && !item.subItems.isEmpty()) {
                // 체크 해제시 UI에서 하위 항목들을 제거하되, 데이터는 유지
                int count = 0;
                for (int i = 0; i < items.size(); i++) {
                    if (i > position && items.get(i).getParent() == item) {
                        count++;
                    }
                }

                if (count > 0) {
                    items.removeAll(item.subItems);
                    notifyDataSetChanged();
                }
            }
        });

    }*/
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
