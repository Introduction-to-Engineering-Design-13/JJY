package com.example.ootd_recommendation_app.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ootd_recommendation_app.data.AppDatabase;
import com.example.ootd_recommendation_app.data.ChecklistDao;
import com.example.ootd_recommendation_app.data.ChecklistItemEntity;
import com.example.ootd_recommendation_app.ui.item.ChecklistItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChecklistRepository {

    private final ChecklistDao checklistDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ChecklistRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.checklistDao = db.checklistDao();
    }

    public LiveData<List<ChecklistItem>> getAllAsChecklistItems() {
        MutableLiveData<List<ChecklistItem>> liveData = new MutableLiveData<>();

        executor.execute(() -> {
            List<ChecklistItemEntity> entities = checklistDao.getAll();
            List<ChecklistItem> result = new ArrayList<>();
            for (ChecklistItemEntity entity : entities) {
                result.add(toChecklistItem(entity));
            }
            liveData.postValue(result);
        });

        return liveData;
    }

    private ChecklistItem toChecklistItem(ChecklistItemEntity entity) {
        ChecklistItem item = new ChecklistItem(entity.getName(), false, 2);
        Log.d("TO_ITEM", "변환 대상: name=" + entity.getName() + ", season=" + entity.getSeason() + ", category=" + entity.getCategory());


        if (entity.getSeason() != null && !entity.getSeason().isEmpty()) {
            String[] seasons = entity.getSeason().split(",");
            for (String s : seasons) item.addSeason(s.trim());
        } else {
            Log.w("TO_ITEM", "시즌 없음!");
        }

        // 🔹 category 설정
        if (entity.getCategory() != null) {
            ChecklistItem parent = new ChecklistItem(entity.getCategory(), false, 1);
            ChecklistItem grandparent = new ChecklistItem(entity.getCategory(), false, 0);
            parent.setParent(grandparent);
            item.setParent(parent);
        }

        return item;
    }
    public void insert(ChecklistItemEntity item) {
        executor.execute(() -> checklistDao.insert(item));
    }
    public List<ChecklistItemEntity> getAll() {
        return checklistDao.getAll();
    }
    public void delete(ChecklistItemEntity item) {
        executor.execute(() -> checklistDao.delete(item));
    }

    public void clearAll() {
        executor.execute(() -> checklistDao.deleteAll());
    }
}
