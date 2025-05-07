package com.example.ootd_recommendation_app.repository;

import android.app.Application;

import com.example.ootd_recommendation_app.data.AppDatabase;
import com.example.ootd_recommendation_app.data.ChecklistDao;
import com.example.ootd_recommendation_app.data.ChecklistItemEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class ChecklistRepository {
    private ChecklistDao checklistDao;

    public ChecklistRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        checklistDao = db.checklistDao();
    }

    public void insert(ChecklistItemEntity item) {
        Executors.newSingleThreadExecutor().execute(() -> checklistDao.insert(item));
    }
    public void delete(ChecklistItemEntity item) {
        Executors.newSingleThreadExecutor().execute(() -> checklistDao.delete(item));
    }
    public List<ChecklistItemEntity> getAll() {
        return checklistDao.getAll(); // 비동기 처리 필요 시 LiveData로 래핑
    }

    public void clearAll() {
        Executors.newSingleThreadExecutor().execute(() -> checklistDao.deleteAll());
    }
}