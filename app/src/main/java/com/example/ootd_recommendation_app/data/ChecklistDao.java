package com.example.ootd_recommendation_app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChecklistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChecklistItemEntity item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChecklistItemEntity> items);

    @Query("SELECT * FROM checklist_items")
    List<ChecklistItemEntity> getAll();

    @Query("DELETE FROM checklist_items")
    void deleteAll();
    @Delete
    void delete(ChecklistItemEntity item);
}
