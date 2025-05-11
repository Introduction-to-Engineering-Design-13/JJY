package com.example.ootd_recommendation_app.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "checklist_items")
public class ChecklistItemEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "path")  // ex: 상의 → 후드 → 빨강
    public String path;
    @ColumnInfo(name = "season")
    public String season;
    @ColumnInfo(name = "category") // 🔹 추가
    public String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public ChecklistItemEntity(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getSeason() {
        return season;
    }

    public String getPath() {
        return path;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setPath(String path) {
        this.path = path;
    }
    // Room이 내부적으로 필요로 하는 기본 생성자도 있으면 좋음
    public ChecklistItemEntity() {}
}