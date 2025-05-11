package com.example.ootd_recommendation_app.recommendation;

import com.example.ootd_recommendation_app.ui.item.ChecklistItem;

public class RecommendedOutfit {
    public ChecklistItem top;
    public ChecklistItem bottom;
    public ChecklistItem outer;
    public ChecklistItem shoes;

    public RecommendedOutfit(ChecklistItem top, ChecklistItem bottom, ChecklistItem outer, ChecklistItem shoes) {
        this.top = top;
        this.bottom = bottom;
        this.outer = outer;
        this.shoes = shoes;
    }

    public String getSummary() {
        return "Top: " + top.getFullPath() + "\nBottom: " + bottom.getFullPath() +
                (outer != null ? "\nOuter: " + outer.getFullPath() : "") +
                "\nShoes: " + shoes.getFullPath();
    }
}