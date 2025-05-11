package com.example.ootd_recommendation_app.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ootd_recommendation_app.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        TextView weatherTextView = root.findViewById(R.id.weatherTextView);
        Button springBtn = root.findViewById(R.id.buttonSpring);
        Button summerBtn = root.findViewById(R.id.buttonSummer);
        Button fallBtn = root.findViewById(R.id.buttonFall);
        Button winterBtn = root.findViewById(R.id.buttonWinter);

        // ViewModel 생성
        galleryViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(GalleryViewModel.class);

        // 🔹 날씨 API 키 설정
        String apiKey = "여기에_기상청_API_키_입력";
        galleryViewModel.fetchWeather(apiKey);

        // 🔹 날씨 텍스트 옵저빙
        galleryViewModel.getWeatherText().observe(getViewLifecycleOwner(), weatherTextView::setText);

        // 🔹 계절 버튼 클릭 시 이동
        springBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_galleryFragment_to_springFragment));
        summerBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_galleryFragment_to_summerFragment));
        fallBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_galleryFragment_to_fallFragment));
        winterBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_galleryFragment_to_winterFragment));

        return root;
    }
}