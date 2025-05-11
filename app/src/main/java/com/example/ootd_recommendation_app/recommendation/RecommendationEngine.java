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
        List<ChecklistItem> tops = new ArrayList<>();
        List<ChecklistItem> bottoms = new ArrayList<>();
        List<ChecklistItem> outers = new ArrayList<>();
        List<ChecklistItem> shoes = new ArrayList<>();

        for (ChecklistItem item : selectedItems) {
            if (item.getSeasons().contains(season)) {
                String category = item.getParent() != null ? item.getParent().getParent().getText() : null;
                if ("상의".equals(category)) tops.add(item);
                else if ("하의".equals(category)) bottoms.add(item);
                else if ("아우터".equals(category)) outers.add(item);
                else if ("신발".equals(category)) shoes.add(item);
            }
        }
        Log.d("RECOMMENDER", "상의 수: " + tops.size() + ", 하의 수: " + bottoms.size() + ", 신발 수: " + shoes.size());
        List<RecommendedOutfit> outfits = new ArrayList<>();
        Random random = new Random();

        for (ChecklistItem top : tops) {
            for (ChecklistItem bottom : bottoms) {
                for (ChecklistItem shoe : shoes) {
                    if (colorsMatch(top, bottom, shoe)) {
                        ChecklistItem selectedOuter = null;

                        // 🔹 계절별 아우터 조건 적용
                        if ("winter".equals(season)) {
                            if (!outers.isEmpty()) {
                                selectedOuter = outers.get(random.nextInt(outers.size()));
                            }
                        } else if ("spring".equals(season) || "fall".equals(season)) {
                            if (!outers.isEmpty() && random.nextBoolean()) {
                                selectedOuter = outers.get(random.nextInt(outers.size()));
                            }
                        } else if ("summer".equals(season)) {
                            if (!outers.isEmpty() && random.nextInt(10) == 0) { // 10% 확률
                                selectedOuter = outers.get(random.nextInt(outers.size()));
                            }
                        }

                        outfits.add(new RecommendedOutfit(top, bottom, selectedOuter, shoe));
                    }
                }
            }
        }

        return outfits;
    }

    private static boolean colorsMatch(ChecklistItem top, ChecklistItem bottom, ChecklistItem shoe) {
        String t = normalizeColor(top.getText());
        String b = normalizeColor(bottom.getText());
        String s = normalizeColor(shoe.getText());

        // 🔹 단순한 색조합 규칙 예시: 톤온톤 / 무채색 포함 / 유사 색조
        return (t.equals(b) || b.equals(s)) ||
                (isNeutral(t) && !isNeutral(b)) ||
                (isSameGroup(t, b) && isSameGroup(b, s));
    }

    private static boolean isNeutral(String color) {
        return color.equalsIgnoreCase("Black") || color.equalsIgnoreCase("White") || color.equalsIgnoreCase("Gray") || color.equalsIgnoreCase("Charcoal") || color.equalsIgnoreCase("Beige");
    }

    private static boolean isSameGroup(String a, String b) {
        return getGroup(a).equals(getGroup(b));
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

    private static String normalizeColor(String color) {
        return color.trim().toLowerCase();
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
}

