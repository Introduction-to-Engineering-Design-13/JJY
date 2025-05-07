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

    public ChecklistItemEntity(String name) {
        this.name = name;
    }

    // Room이 내부적으로 필요로 하는 기본 생성자도 있으면 좋음
    public ChecklistItemEntity() {}
}