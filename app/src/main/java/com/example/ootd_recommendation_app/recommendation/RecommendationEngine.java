package com.example.ootd_recommendation_app.recommendation;

import android.util.Log;

import com.example.ootd_recommendation_app.ui.item.ChecklistItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecommendationEngine {

    public static List<RecommendedOutfit> generate(List<ChecklistItem> selectedItems, String season) {
        Log.d("RECOMMENDER", "입력된 아이템 수: " + selectedItems.size());
        Log.d("RECOMMENDER", "계절 필터: " + season);

        List<ChecklistItem> topItems = new ArrayList<>();
        List<ChecklistItem> bottomItems = new ArrayList<>();
        List<ChecklistItem> shoesItems = new ArrayList<>();
        List<ChecklistItem> outerItems = new ArrayList<>();

        for (ChecklistItem item : selectedItems) {
            if (item.getSeasons().contains(season)) {
                String category = item.getTopLevelCategory();  // ✔ category 직접 사용

                if ("상의".equals(category)) topItems.add(item);
                else if ("하의".equals(category)) bottomItems.add(item);
                else if ("신발".equals(category)) shoesItems.add(item);
                else if ("아우터".equals(category)) outerItems.add(item);
            }
        }

        Log.d("RECOMMENDER", "상의 수: " + topItems.size() + ", 하의 수: " + bottomItems.size() + ", 신발 수: " + shoesItems.size());

        List<RecommendedOutfit> outfits = new ArrayList<>();
        Random random = new Random();

        for (ChecklistItem topItem : topItems) {
            for (ChecklistItem bottomItem : bottomItems) {
                for (ChecklistItem shoesItem : shoesItems) {
                    if (colorsMatch(topItem, bottomItem, shoesItem)) {
                        ChecklistItem outerItem = null;

                        // 🔹 계절별 아우터 조건
                        if ("winter".equals(season)) {
                            if (!outerItems.isEmpty()) {
                                outerItem = outerItems.get(random.nextInt(outerItems.size()));
                            }
                        } else if ("spring".equals(season) || "fall".equals(season)) {
                            if (!outerItems.isEmpty() && random.nextBoolean()) {
                                outerItem = outerItems.get(random.nextInt(outerItems.size()));
                            }
                        } else if ("summer".equals(season)) {
                            if (!outerItems.isEmpty() && random.nextInt(10) == 0) {
                                outerItem = outerItems.get(random.nextInt(outerItems.size()));
                            }
                        }

                        outfits.add(new RecommendedOutfit(topItem, bottomItem, outerItem, shoesItem));
                    }
                }
            }
        }

        return outfits;
    }

    public static List<ChecklistItem> collectCheckedLeafItemsFromList(List<ChecklistItem> items) {
        List<ChecklistItem> result = new ArrayList<>();
        for (ChecklistItem item : items) {
            if (item.isChecked()) {
                if (item.subItems == null || item.subItems.isEmpty()) {
                    result.add(item);
                } else {
                    result.addAll(collectCheckedLeafItemsFromList(item.subItems));
                }
            }
        }
        return result;
    }

    private static boolean colorsMatch(ChecklistItem top, ChecklistItem bottom, ChecklistItem shoes) {
        String t = normalizeColor(top.getText());
        String b = normalizeColor(bottom.getText());
        String s = normalizeColor(shoes.getText());

        return (t.equals(b) || b.equals(s)) ||
                (isNeutral(t) && !isNeutral(b)) ||
                (isSameGroup(t, b) && isSameGroup(b, s));
    }

    private static boolean isNeutral(String color) {
        return color.equalsIgnoreCase("Black") || color.equalsIgnoreCase("White") || color.equalsIgnoreCase("Gray") ||
                color.equalsIgnoreCase("Charcoal") || color.equalsIgnoreCase("Beige");
    }

    private static boolean isSameGroup(String a, String b) {
        return getGroup(a).equals(getGroup(b));
    }

    private static String getGroup(String color) {
        switch (normalizeColor(color)) {
            case "navy":
            case "blue":
            case "deep blue":
            case "light blue":
            case "sky blue":
                return "blue";
            case "red":
            case "pink":
            case "wine":
            case "coral":
                return "red";
            case "green":
            case "olive":
            case "khaki":
            case "mint":
                return "green";
            case "brown":
            case "beige":
            case "tan":
            case "ivory":
                return "brown";
            case "gray":
            case "white":
            case "black":
            case "charcoal":
                return "neutral";
            case "yellow":
            case "mustard":
                return "yellow";
            default:
                return "other";
        }
    }

    private static String normalizeColor(String color) {
        return color.trim().toLowerCase();
    }
}
