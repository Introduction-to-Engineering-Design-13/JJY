package com.example.ootd_recommendation_app.weather;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("response")
    public Response response;

    public static class Response {
        @SerializedName("body")
        public Body body;
    }

    public static class Body {
        @SerializedName("items")
        public Items items;
    }

    public static class Items {
        @SerializedName("item")
        public List<Item> item;
    }

    public static class Item {
        @SerializedName("category")
        public String category;

        @SerializedName("fcstValue")
        public String value;

        @SerializedName("fcstTime")
        public String time;
    }
}