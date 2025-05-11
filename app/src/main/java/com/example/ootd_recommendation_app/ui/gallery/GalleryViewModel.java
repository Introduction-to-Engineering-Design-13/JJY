package com.example.ootd_recommendation_app.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ootd_recommendation_app.weather.WeatherViewModel;

public class GalleryViewModel extends AndroidViewModel {

    private final WeatherViewModel weatherViewModel;

    public GalleryViewModel(@NonNull Application application) {
        super(application);
        weatherViewModel = new WeatherViewModel(application);
    }

    public LiveData<String> getWeatherText() {
        return weatherViewModel.getWeatherData();
    }

    public void fetchWeather(String apiKey) {
        weatherViewModel.fetchWeather(apiKey);
    }
}