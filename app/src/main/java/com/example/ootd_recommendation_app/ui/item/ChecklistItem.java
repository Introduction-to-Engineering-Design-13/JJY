package com.example.ootd_recommendation_app.ui.item;

import java.util.ArrayList;
import java.util.List;

public class ChecklistItem {
    private String text;
    private boolean checked;
    public ChecklistItem parent; // 부모항목 추가
    public List<ChecklistItem> subItems; // 하위 항목들 (nullable)
    private int depth; // 들여쓰기 깊이 (0 = 최상위)

    public ChecklistItem(String text, boolean checked,int depth) {
        this.text = text;
        this.checked = checked;
        this.depth = depth;
        this.subItems = new ArrayList<>(); // 여기!
    }

    public void setParent(ChecklistItem parent) {
        this.parent = parent;
    }

    public ChecklistItem getParent() {
        return parent;
    }
    public String getFullPath() {
        StringBuilder path = new StringBuilder(this.text);
        ChecklistItem current = parent;
        while (current != null) {
            path.insert(0, current.text + " → ");
            current = current.getParent();
        }
        return path.toString();
    }
    // Getter & Setter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public int getDepth() { return depth; }
    public void setDepth(int depth) { this.depth = depth; }
}