package com.example.day3.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.day3.BuildConfig;
import com.example.day3.R;
import com.example.day3.model.WeatherBean;
import com.example.day3.viewmodel.WeatherViewModel;

import java.util.List;

public class CurrentWeatherFragment extends Fragment
{

    private WeatherViewModel weatherViewModel;
    private TextView tvCity, tvTemperature, tvWeather;
    private Button btnBeijing, btnShanghai, btnGuangzhou, btnShenzhen;

    // 城市代码映射
    private final String[] cityCodes = {"110000", "310000", "440100", "440300"};
    private final String[] cityNames = {"北京", "上海", "广州", "深圳"};

    public CurrentWeatherFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setupCityButtons();
        setupViewModel();
        observeWeatherData();

        // 默认加载第一个城市（北京）的天气
        loadWeatherForCity(0);
    }

    private void initViews(View view)
    {
        tvCity = view.findViewById(R.id.id_tv_firstpage_city);
        tvWeather = view.findViewById(R.id.id_tv_firstpage_weather_cn);
        tvTemperature = view.findViewById(R.id.id_tv_firstpage_weather);

        // 城市选择按钮
        btnBeijing = view.findViewById(R.id.btn_beijing);
        btnShanghai = view.findViewById(R.id.btn_shanghai);
        btnGuangzhou = view.findViewById(R.id.btn_guangzhou);
        btnShenzhen = view.findViewById(R.id.btn_shenzhen);
    }

    // 在现有代码基础上，修改城市按钮点击事件
    private void setupCityButtons()
    {
        // 北京按钮
        btnBeijing.setOnClickListener(v ->
        {
            updateButtonState(0);
            loadWeatherForCity(0);
            weatherViewModel.setCurrentCity("北京");
        });
        // 上海按钮
        btnShanghai.setOnClickListener(v ->
        {
            updateButtonState(1);
            loadWeatherForCity(1);
            weatherViewModel.setCurrentCity("上海");
        });
        // 广州按钮
        btnGuangzhou.setOnClickListener(v ->
        {
            updateButtonState(2);
            loadWeatherForCity(2);
            weatherViewModel.setCurrentCity("广州");
        });
        // 深圳按钮
        btnShenzhen.setOnClickListener(v ->
        {
            updateButtonState(3);
            loadWeatherForCity(3);
            weatherViewModel.setCurrentCity("深圳");
        });
    }

    private void setupViewModel()
    {
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
    }

    private void observeWeatherData()
    {
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherBean ->
        {
            if (weatherBean != null && "1".equals(weatherBean.getStatus()))
            {
                updateWeatherUI(weatherBean);
            }
            else
            {
                Log.d("WeatherDebug", "数据无效，不更新UI");
            }
        });
    }

    private void loadWeatherForCity(int cityIndex)
    {
        String apiKey = BuildConfig.AMAP_API_KEY;
        String cityCode = cityCodes[cityIndex];
        weatherViewModel.fetchWeather(cityCode, apiKey);
    }

    private void updateButtonState(int selectedIndex)
    {
        // 重置所有按钮状态
        btnBeijing.setBackgroundResource(R.drawable.city_button_normal);
        btnShanghai.setBackgroundResource(R.drawable.city_button_normal);
        btnGuangzhou.setBackgroundResource(R.drawable.city_button_normal);
        btnShenzhen.setBackgroundResource(R.drawable.city_button_normal);

//        btnBeijing.setTextColor(getResources().getColor(R.color.textSecondary));
//        btnShanghai.setTextColor(getResources().getColor(R.color.textSecondary));
//        btnGuangzhou.setTextColor(getResources().getColor(R.color.textSecondary));
//        btnShenzhen.setTextColor(getResources().getColor(R.color.textSecondary));

        // 设置选中按钮状态
        switch (selectedIndex)
        {
            case 0:
                btnBeijing.setBackgroundResource(R.drawable.city_button_selected);
//                btnBeijing.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                btnShanghai.setBackgroundResource(R.drawable.city_button_selected);
//                btnShanghai.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                btnGuangzhou.setBackgroundResource(R.drawable.city_button_selected);
//                btnGuangzhou.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                btnShenzhen.setBackgroundResource(R.drawable.city_button_selected);
//                btnShenzhen.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    private void updateWeatherUI(WeatherBean weatherBean)
    {
        if (weatherBean == null)
        {
            Log.e("WeatherDebug", "weatherBean为null");
            return;
        }
        // 优先从预测数据获取城市信息
        String cityName = "未知城市";

        // 方法A：从预测数据获取城市
        if (weatherBean.getForecasts() != null &&
                !weatherBean.getForecasts().isEmpty() &&
                weatherBean.getForecasts().get(0) != null)
        {
            cityName = weatherBean.getForecasts().get(0).getCity();
        }
        // 方法B：从实时数据获取城市（如果存在）
        else if (weatherBean.getLives() != null &&
                !weatherBean.getLives().isEmpty() &&
                weatherBean.getLives().get(0) != null)
        {
            cityName = weatherBean.getLives().get(0).getCity();
        }

        // 更新城市显示
        if (tvCity != null)
        {
            tvCity.setText(cityName);
        }
        else
        {
            Log.e("WeatherDebug", "tvCity为null");
        }

        // 处理实时天气数据
        if (weatherBean.getLives() != null && !weatherBean.getLives().isEmpty())
        {
            WeatherBean.LivesBean currentWeather = weatherBean.getLives().get(0);

            // 更新温度、天气等信息
            if (tvTemperature != null)
            {
                tvTemperature.setText(currentWeather.getTemperature() + "°");
                Log.d("WeatherDebug", "温度更新为: " + currentWeather.getTemperature() + "°");
            }
            if (tvWeather != null)
            {
                tvWeather.setText(currentWeather.getWeather());
                Log.d("WeatherDebug", "天气更新为: " + currentWeather.getWeather());
            }
        } else {
            Log.w("WeatherDebug", "实时天气数据为空，使用预测数据的第一天作为当前天气");
            // 如果没有实时数据，使用预测数据的第一天
            if (weatherBean.getForecasts() != null &&
                    !weatherBean.getForecasts().isEmpty() &&
                    weatherBean.getForecasts().get(0) != null &&
                    weatherBean.getForecasts().get(0).getCasts() != null &&
                    !weatherBean.getForecasts().get(0).getCasts().isEmpty()) {

                WeatherBean.ForecastsBean.CastsBean firstDay =
                        weatherBean.getForecasts().get(0).getCasts().get(0);

                if (tvTemperature != null) {
                    tvTemperature.setText(firstDay.getDaytemp() + "°");
                    Log.d("WeatherDebug", "使用预测温度: " + firstDay.getDaytemp() + "°");
                }
                if (tvWeather != null) {
                    tvWeather.setText(firstDay.getDayweather());
                    Log.d("WeatherDebug", "使用预测天气: " + firstDay.getDayweather());
                }
            }
        }

        // 更新温度范围
        updateTemperatureRange(weatherBean);

        // 更新白天/夜间卡片
        updateDayNightCards(weatherBean);

        // 更新预测天气UI
        updateForecastUI(weatherBean.getForecasts());

        Log.d("WeatherDebug", "===== UI更新完成 =====");
    }

    // 新增方法：更新预测天气UI
    private void updateForecastUI(List<WeatherBean.ForecastsBean> forecasts) {
        if (forecasts == null || forecasts.isEmpty()) {
            Log.w("WeatherDebug", "预测天气数据为空");
            return;
        }

        WeatherBean.ForecastsBean forecast = forecasts.get(0);
        if (forecast == null || forecast.getCasts() == null || forecast.getCasts().isEmpty()) {
            Log.w("WeatherDebug", "预测天气casts数据为空");
            return;
        }

        List<WeatherBean.ForecastsBean.CastsBean> casts = forecast.getCasts();


    }

    // 辅助方法：更新单日预测天气
    private void updateForecastDay(WeatherBean.ForecastsBean.CastsBean cast,
                                   int weatherTextId, int tempTextId, int windTextId) {
        if (cast == null) return;

        View view = getView();
        if (view == null) return;

        TextView tvWeather = view.findViewById(weatherTextId);
        TextView tvTemp = view.findViewById(tempTextId);
        TextView tvWind = view.findViewById(windTextId);

        if (tvWeather != null) {
            tvWeather.setText(cast.getDayweather());
        }
        if (tvTemp != null) {
            tvTemp.setText(cast.getDaytemp() + "°");
        }
        if (tvWind != null) {
            String windInfo = cast.getDaywind() + "风 " + cast.getDaypower() + "级";
            tvWind.setText(windInfo);
        }
    }

    // 新增方法：更新温度范围
    private void updateTemperatureRange(WeatherBean weatherBean)
    {
        if (weatherBean.getForecasts() != null && !weatherBean.getForecasts().isEmpty())
        {
            List<WeatherBean.ForecastsBean.CastsBean> casts = weatherBean.getForecasts().get(0).getCasts();
            if (casts != null && !casts.isEmpty())
            {
                WeatherBean.ForecastsBean.CastsBean today = casts.get(0);

                // 更新温度范围显示
                TextView tvTempRange = getView().findViewById(R.id.tv_temp_range);
                if (tvTempRange != null)
                {
                    tvTempRange.setText("最高:" + today.getDaytemp() + "° 最低:" + today.getNighttemp() + "°");
                }
            }
        }
    }

    // 新增方法：更新白天/夜间卡片
    private void updateDayNightCards(WeatherBean weatherBean)
    {
        if (weatherBean.getForecasts() != null && !weatherBean.getForecasts().isEmpty())
        {
            List<WeatherBean.ForecastsBean.CastsBean> casts = weatherBean.getForecasts().get(0).getCasts();
            if (casts != null && !casts.isEmpty())
            {
                WeatherBean.ForecastsBean.CastsBean today = casts.get(0);

                // 更新白天卡片
                TextView tvDayWeather = getView().findViewById(R.id.id_tv_day_weather_cn);
                TextView tvDayTemp = getView().findViewById(R.id.id_tv_day_weather);
                TextView tvDayWind = getView().findViewById(R.id.id_tv_day_wind);

                if (tvDayWeather != null) tvDayWeather.setText(today.getDayweather());
                if (tvDayTemp != null) tvDayTemp.setText(today.getDaytemp() + "°");
                if (tvDayWind != null) tvDayWind.setText(today.getDaywind() + " " + today.getDaypower());

                // 更新夜间卡片
                TextView tvNightWeather = getView().findViewById(R.id.id_tv_night_weather_cn);
                TextView tvNightTemp = getView().findViewById(R.id.id_tv_night_weather);
                TextView tvNightWind = getView().findViewById(R.id.id_tv_night_wind);

                if (tvNightWeather != null) tvNightWeather.setText(today.getNightweather());
                if (tvNightTemp != null) tvNightTemp.setText(today.getNighttemp() + "°");
                if (tvNightWind != null) tvNightWind.setText(today.getNightwind() + " " + today.getNightpower());
            }
        }
    }


    private String formatTime(String reportTime)
    {
        if (reportTime != null && reportTime.length() >= 16)
        {
            return reportTime.substring(11, 16);
        }
        return reportTime;
    }
}