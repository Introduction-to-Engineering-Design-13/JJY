package com.example.ootd_recommendation_app.ui.gallery;

import android.os.Bundle;
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

public class SummerFragment extends Fragment {

    private TextView resultTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summer, container, false);
        resultTextView = view.findViewById(R.id.textResult);

        // 🔹 ViewModel 또는 Repository에서 LiveData로 옷 가져오기
        ChecklistRepository repo = new ChecklistRepository(requireActivity().getApplication());
        repo.getAllAsChecklistItems().observe(getViewLifecycleOwner(), items -> {
            List<RecommendedOutfit> outfits = RecommendationEngine.generate(items, "summer");

            StringBuilder builder = new StringBuilder();
            for (RecommendedOutfit outfit : outfits) {
                builder.append(outfit.getSummary()).append("\n\n");
            }

            resultTextView.setText(builder.toString().isEmpty() ? "추천 결과 없음" : builder.toString());
        });

        return view;
    }
}
