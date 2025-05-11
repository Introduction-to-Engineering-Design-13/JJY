package com.example.ootd_recommendation_app.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ootd_recommendation_app.R;
import com.example.ootd_recommendation_app.recommendation.RecommendationEngine;
import com.example.ootd_recommendation_app.recommendation.RecommendedOutfit;
import com.example.ootd_recommendation_app.repository.ChecklistRepository;
import com.example.ootd_recommendation_app.ui.item.ChecklistItem;

import java.util.List;

public class SpringFragment extends Fragment {

    private TextView resultTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spring, container, false);
        resultTextView = view.findViewById(R.id.textResult);

        // 🔹 ViewModel 또는 Repository에서 LiveData로 옷 가져오기
        ChecklistRepository repo = new ChecklistRepository(requireActivity().getApplication());
        repo.getAllAsChecklistItems().observe(getViewLifecycleOwner(), items -> {
            Log.d("SPRING_FRAGMENT", "불러온 아이템 수: " + items.size());
            for (ChecklistItem item : items) {
                Log.d("SPRING_FRAGMENT", "아이템: " + item.getText() + ", 시즌들: " + item.getSeasons());
            }

            List<RecommendedOutfit> outfits = RecommendationEngine.generate(items, "spring");

            StringBuilder builder = new StringBuilder();
            for (RecommendedOutfit outfit : outfits) {
                builder.append(outfit.getSummary()).append("\n\n");
            }

            resultTextView.setText(builder.toString().isEmpty() ? "추천 결과 없음" : builder.toString());
        });

        return view;
    }
}
