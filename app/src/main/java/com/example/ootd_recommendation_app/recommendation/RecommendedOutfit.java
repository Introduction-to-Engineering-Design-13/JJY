package com.example.ootd_recommendation_app.recommendation;

import com.example.ootd_recommendation_app.ui.item.ChecklistItem;

public class RecommendedOutfit {
    public ChecklistItem topItem;
    public ChecklistItem bottomItem;
    public ChecklistItem outerItem;
    public ChecklistItem shoesItem;

    public RecommendedOutfit(ChecklistItem topItem, ChecklistItem bottomItem, ChecklistItem outerItem, ChecklistItem shoesItem) {
        this.topItem = topItem;
        this.bottomItem = bottomItem;
        this.outerItem = outerItem;
        this.shoesItem = shoesItem;
    }

    public String getSummary() {
        return "Top: " + topItem.getFullPath() + "\nBottom: " + bottomItem.getFullPath() +
                (outerItem != null ? "\nOuter: " + outerItem.getFullPath() : "") +
                "\nShoes: " + shoesItem.getFullPath();
    }
}
