package com.example.ootd_recommendation_app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    //viewmodel에서 전체 데이터 관리 하기

    private final MutableLiveData<List<String>> checkedItems = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<String>> getCheckedItems() {
        return checkedItems;
    }

    public void addCheckedItems(List<String> newItems) {
        List<String> currentList = checkedItems.getValue();
        if (currentList != null) {
            for (String item : newItems) {
                if (!currentList.contains(item)) {
                    currentList.add(item);
                }
            }
            checkedItems.setValue(currentList);
        }
    }

    public void removeItem(String item) {
        List<String> currentList = checkedItems.getValue();
        if (currentList != null && currentList.contains(item)) {
            currentList.remove(item);
            checkedItems.setValue(currentList);
        }
    }
}



//수정 전
/* private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}*/