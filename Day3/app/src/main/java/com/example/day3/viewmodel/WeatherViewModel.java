package com.example.day3.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.day3.model.WeatherBean;
import com.example.day3.network.RetrofitClient;
import com.example.day3.network.WeatherApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel
{
    private MutableLiveData<WeatherBean> weatherData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private WeatherApi weatherApi;
    // 添加当前选中的城市
    private MutableLiveData<String> currentCity = new MutableLiveData<>("北京");

    public LiveData<String> getCurrentCity()
    {
        return currentCity;
    }

    public void setCurrentCity(String city)
    {
        currentCity.setValue(city);
    }
    public WeatherViewModel()
    {
        weatherApi = RetrofitClient.getInstance().create(WeatherApi.class);
    }

    public void fetchWeather(String cityCode, String apiKey)
    {
        isLoading.setValue(true);

        // 打印请求URL，确认参数正确
        Log.d("Weather", "请求URL: city=" + cityCode + ", extensions=all");

        Call<WeatherBean> call = weatherApi.getWeatherInfo(apiKey, cityCode, "all");
        call.enqueue(new Callback<WeatherBean>()
        {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response)
            {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null)
                {
                    WeatherBean weatherBean = response.body();
                    Log.d("Weather", "状态: " + weatherBean.getStatus());
                    Log.d("Weather", "信息: " + weatherBean.getInfo());
                    Log.d("Weather", "实时天气数量: " + (weatherBean.getLives() != null ? weatherBean.getLives().size() : 0));
                    Log.d("Weather", "预测天气城市数量: " + (weatherBean.getForecasts() != null ? weatherBean.getForecasts().size() : 0));

                    if (weatherBean.getForecasts() != null && weatherBean.getForecasts().size() > 0)
                    {
                        List<WeatherBean.ForecastsBean.CastsBean> casts = weatherBean.getForecasts().get(0).getCasts();
                        Log.d("Weather", "预测天数: " + (casts != null ? casts.size() : 0));
                        if (casts != null)
                        {
                            for (int i = 0; i < casts.size(); i++)
                            {
                                WeatherBean.ForecastsBean.CastsBean cast = casts.get(i);
                                Log.d("Weather", "第" + (i+1) + "天: " + cast.getDate() + " " + cast.getDayweather() + " " + cast.getDaytemp() + "°");
                            }
                        }
                    }
                    weatherData.setValue(weatherBean);
                }
                else
                {
                    errorMessage.setValue("数据获取失败: " + response.code());
                    Log.e("Weather", "请求失败: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t)
            {
                isLoading.setValue(false);
                errorMessage.setValue("网络请求失败: " + t.getMessage());
                Log.e("Weather", "网络请求失败", t);
            }
        });
    }

    public LiveData<WeatherBean> getWeatherData()
    {
        return weatherData;
    }

    public LiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public LiveData<String> getErrorMessage()
    {
        return errorMessage;
    }



}
