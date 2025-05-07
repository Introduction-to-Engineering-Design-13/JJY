package com.example.ootd_recommendation_app.weather;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ootd_recommendation_app.weather.GridUtil.LatXLngY;
import com.example.ootd_recommendation_app.weather.LocationUtil.LocationCallback;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherViewModel extends AndroidViewModel {

    private final MutableLiveData<String> weatherData = new MutableLiveData<>();
    private final Application application;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<String> getWeatherData() {
        return weatherData;
    }

    public void fetchWeather(String serviceKey) {
        LocationUtil.getCurrentLocation(application.getApplicationContext(), new LocationCallback() {
            @Override
            public void onLocationResult(Location location) {
                if (location != null) {
                    LatXLngY grid = GridUtil.convertGRID_GPS(location.getLatitude(), location.getLongitude());
                    String baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
                    String baseTime = getBaseTime();

                    WeatherService service = RetrofitClient.getClient().create(WeatherService.class);
                    Call<WeatherResponse> call = service.getForecast(
                            serviceKey, 100, 1, "JSON", baseDate, baseTime, grid.x, grid.y
                    );

                    call.enqueue(new Callback<WeatherResponse>() {
                        @Override
                        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Log.d("WeatherAPI", "응답 성공: " + new Gson().toJson(response.body()));
                                for (WeatherResponse.Item item : response.body().response.body.items.item) {
                                    if ("TMP".equals(item.category)) {
                                        weatherData.postValue("기온: " + item.value + "℃");
                                        return;
                                    }
                                }
                                weatherData.postValue("기온 항목 없음");
                            } else {
                                Log.e("WeatherAPI", "응답 실패 또는 body 없음");
                                weatherData.postValue("날씨 데이터를 불러올 수 없습니다.");
                            }
                        }
                        @Override
                        public void onFailure(Call<WeatherResponse> call, Throwable t) {
                            Log.e("WeatherAPI", "API 호출 실패: " + t.getMessage(), t);
                            weatherData.postValue("날씨 API 호출 실패");
                        }
                    });
                }
            }
        });
    }

    private String getBaseTime() {
        // 현재 시간 기준으로 가장 가까운 발표 시간을 계산
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm", Locale.getDefault());
        int now = Integer.parseInt(sdf.format(new Date()));
        if (now < 210) return "0200";
        else if (now < 510) return "0500";
        else if (now < 810) return "0800";
        else if (now < 1110) return "1100";
        else if (now < 1410) return "1400";
        else if (now < 1710) return "1700";
        else if (now < 2010) return "2000";
        else if (now < 2310) return "2300";
        else return "0200";
    }
}