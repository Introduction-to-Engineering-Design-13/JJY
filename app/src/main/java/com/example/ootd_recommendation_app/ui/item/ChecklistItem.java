package com.example.ootd_recommendation_app.ui.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChecklistItem {
    private String text;
    private boolean checked;
    public ChecklistItem parent; // 부모항목 추가
    public List<ChecklistItem> subItems; // 하위 항목들 (nullable)
    private int depth; // 들여쓰기 깊이 (0 = 최상위)
    private List<String> seasons = new ArrayList<>(); // 계절 정보 추가

    public void setSubItems(List<ChecklistItem> children) {
        this.subItems = children;
        for (ChecklistItem child : children) {
            child.setParent(this); // 자동 parent 설정
        }
    }

    public ChecklistItem(String text, boolean checked,int depth) {
        this.text = text;
        this.checked = checked;
        this.depth = depth;
        this.subItems = new ArrayList<>();
        this.seasons = new ArrayList<>();
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

    public List<String> getSeasons() {
        return seasons;
    }

    public Set<String> getEffectiveSeasons() {
        ChecklistItem current = this;
        while (current != null) {
            if (!current.getSeasons().isEmpty()) {
                return new HashSet<>(current.getSeasons());
            }
            current = current.getParent();
        }
        return new HashSet<>(); // 아무 season도 못 찾은 경우
    }
    public String getTopLevelCategory() {
        ChecklistItem current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current.getText();
    }


    public void addSeason(String season) {
        if (!seasons.contains(season)) {
            seasons.add(season);
        }
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