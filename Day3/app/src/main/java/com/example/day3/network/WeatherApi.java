package com.example.day3.network;

import com.example.day3.model.WeatherBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi
{
    @GET("v3/weather/weatherInfo")
    Call<WeatherBean> getWeatherInfo(
            @Query("key") String key,
            @Query("city") String cityCode,
            @Query("extensions") String extensions  // "base"实时天气，"all"预测天气
    );
}