package com.example.ootd_recommendation_app.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.ootd_recommendation_app.data.ChecklistItemEntity;
import com.example.ootd_recommendation_app.repository.ChecklistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {
    //viewmodel에서 전체 데이터 관리 하기
    private ChecklistRepository repository;
    private final MutableLiveData<List<String>> checkedItems = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<String>> getCheckedItems() {
        return checkedItems;
    }

    public void addCheckedItems(List<String> newItems) {
        List<String> currentList = checkedItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        List<String> updatedList = new ArrayList<>(currentList);
        for (String item : newItems) {
            if (!updatedList.contains(item)) {
                updatedList.add(item);
                repository.insert(new ChecklistItemEntity(item)); // DB에 저장
            }
        }

        checkedItems.postValue(updatedList);
    }
    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new ChecklistRepository(application);
        loadItemsFromDb();
    }

    private void loadItemsFromDb() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ChecklistItemEntity> items = repository.getAll();
            List<String> paths = new ArrayList<>();
            for (ChecklistItemEntity item : items) {
                // ✅ 경로 전체를 표시 (ex: 상의 → 후드 → Black)
                paths.add(item.getPath());
            }
            checkedItems.postValue(paths); // LiveData에 반영
        });
    }

}
