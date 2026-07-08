package com.example.day3.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.day3.BuildConfig;
import com.example.day3.R;
import com.example.day3.adapter.ForecastAdapter;
import com.example.day3.model.WeatherBean;
import com.example.day3.viewmodel.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ForecastFragment extends Fragment
{
    private WeatherViewModel weatherViewModel;
    private ForecastAdapter forecastAdapter;
    private RecyclerView recyclerView;
    private TextView tvForecastCity;
    private String currentCity = "北京"; // 记录当前显示的城市


    public ForecastFragment()
    {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupViewModel();
        observeWeatherData();

        // 只添加这个城市变化监听
        observeCityChanges();
        // 确保初始数据加载
        loadInitialData();
    }

    // 新增方法：加载初始数据
    private void loadInitialData() {
        // 如果已经有数据，直接更新UI
        if (weatherViewModel.getWeatherData().getValue() != null) {
            WeatherBean existingData = weatherViewModel.getWeatherData().getValue();
            if (existingData != null && "1".equals(existingData.getStatus())) {
                updateCityDisplay(existingData);
                updateForecastUI(existingData);
            }
        } else {
            // 如果没有数据，触发一次数据加载
            loadWeatherDataForCurrentCity();
        }
    }

    private void initViews(View view)
    {
        recyclerView = view.findViewById(R.id.recyclerView);
        tvForecastCity = view.findViewById(R.id.id_tv_forecast_city); // 新增
    }

    private void setupRecyclerView()
    {
        forecastAdapter = new ForecastAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(forecastAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setupViewModel()
    {
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
    }

    // 新增：监听城市变化（核心功能）
    private void observeCityChanges()
    {
        weatherViewModel.getCurrentCity().observe(getViewLifecycleOwner(), newCity ->
        {
            if (newCity != null && !newCity.equals(currentCity))
            {
                Log.d("ForecastDebug", "检测到城市变化: " + currentCity + " -> " + newCity);
                currentCity = newCity;

                // 城市变化时重新加载预测数据
                loadWeatherDataForCurrentCity(); // 需要添加这个方法
            }
        });
    }

    // 新增方法：为当前城市加载天气数据
    private void loadWeatherDataForCurrentCity() {
        String cityCode = getCityCodeByCityName(currentCity);
        if (cityCode != null) {
            String apiKey = BuildConfig.AMAP_API_KEY;
            weatherViewModel.fetchWeather(cityCode, apiKey);

            // 立即更新城市显示（不等待网络响应）
            if (tvForecastCity != null) {
                tvForecastCity.setText(currentCity);
            }
        }
    }

    // 辅助方法：根据城市名获取城市代码
    private String getCityCodeByCityName(String cityName) {
        switch (cityName) {
            case "北京": return "110000";
            case "上海": return "310000";
            case "广州": return "440100";
            case "深圳": return "440300";
            default: return "110000";
        }
    }


    private void observeWeatherData()
    {
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherBean ->
        {
            if (weatherBean != null && "1".equals(weatherBean.getStatus()))
            {
                // 更新城市显示
                updateCityDisplay(weatherBean);
                // 更新预测天气列表
                updateForecastUI(weatherBean);
            }
        });
    }

    // 新增方法：更新城市显示
    private void updateCityDisplay(WeatherBean weatherBean) {
        String city = currentCity; // 默认使用当前记录的城市

        // 优先从预测数据获取城市名（因为预测数据更稳定）
        if (weatherBean.getForecasts() != null && !weatherBean.getForecasts().isEmpty()) {
            String forecastCity = weatherBean.getForecasts().get(0).getCity();
            if (forecastCity != null && !forecastCity.isEmpty()) {
                city = forecastCity;
            }
        }
        // 其次从实时数据获取
        else if (weatherBean.getLives() != null && !weatherBean.getLives().isEmpty()) {
            String liveCity = weatherBean.getLives().get(0).getCity();
            if (liveCity != null && !liveCity.isEmpty()) {
                city = liveCity;
            }
        }

        if (tvForecastCity != null) {
            tvForecastCity.setText(city);
        }
    }
    private void updateForecastUI(WeatherBean weatherBean)
    {
        if (weatherBean.getForecasts() != null && !weatherBean.getForecasts().isEmpty())
        {
            List<WeatherBean.ForecastsBean.CastsBean> casts =
                    weatherBean.getForecasts().get(0).getCasts();

            if (casts != null)
            {
                // 确保显示7天数据
                if (casts.size() < 7)
                {
                    casts = extendTo7Days(casts);
                }
                forecastAdapter.updateData(casts);
            }
        }
    }

    private List<WeatherBean.ForecastsBean.CastsBean> extendTo7Days(List<WeatherBean.ForecastsBean.CastsBean> originalCasts)
    {
        if (originalCasts == null || originalCasts.isEmpty())
        {
            return originalCasts;
        }

        List<WeatherBean.ForecastsBean.CastsBean> extendedCasts = new ArrayList<>(originalCasts);

        int currentSize = originalCasts.size();
        int neededDays = 7 - currentSize;

        if (neededDays > 0)
        {
            try {
                // 获取最后一天的日期
                String lastDateStr = originalCasts.get(currentSize - 1).getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(lastDateStr));

                // 获取最后一天的星期
                String lastWeek = originalCasts.get(currentSize - 1).getWeek();

                for (int i = 1; i <= neededDays; i++)
                {
                    // 日期加1天
                    calendar.add(Calendar.DAY_OF_MONTH, 1);

                    // 生成新的日期字符串
                    String newDateStr = sdf.format(calendar.getTime());

                    // 计算新的星期
                    String newWeek = calculateNextWeek(lastWeek, i);

                    // 使用最后一天的天气数据作为模板，但创建新的对象
                    WeatherBean.ForecastsBean.CastsBean template = originalCasts.get(currentSize - 1);
                    WeatherBean.ForecastsBean.CastsBean newDay = createRealisticDay(template, newDateStr, newWeek, i);

                    extendedCasts.add(newDay);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                // 如果扩展失败，至少返回原始数据
                return originalCasts;
            }
        }
        return extendedCasts;
    }


    /**
     * 创建更真实的模拟天气数据
     */
    private WeatherBean.ForecastsBean.CastsBean createRealisticDay(
            WeatherBean.ForecastsBean.CastsBean template,
            String date,
            String week,
            int daysAfter)
    {

        WeatherBean.ForecastsBean.CastsBean newDay = new WeatherBean.ForecastsBean.CastsBean();

        // 设置日期和星期
        newDay.setDate(date);
        newDay.setWeek(week);

        // 基于模板设置天气，但做一些随机变化
        Random random = new Random();

        // 天气类型数组
        String[] weatherTypes = {"晴", "多云", "阴", "小雨", "中雨", "阵雨"};

        // 有70%的概率保持相同天气，30%的概率变化
        if (random.nextFloat() < 0.7f)
        {
            newDay.setDayweather(template.getDayweather());
            newDay.setNightweather(template.getNightweather());
        }
        else
        {
            int index = random.nextInt(weatherTypes.length);
            newDay.setDayweather(weatherTypes[index]);
            newDay.setNightweather(weatherTypes[(index + 1) % weatherTypes.length]);
        }

        // 温度：基于模板温度，每天变化±2度
        try
        {
            int baseDayTemp = Integer.parseInt(template.getDaytemp());
            int baseNightTemp = Integer.parseInt(template.getNighttemp());

            int dayTempChange = random.nextInt(5) - 2; // -2到+2的变化
            int nightTempChange = random.nextInt(5) - 2;

            newDay.setDaytemp(String.valueOf(Math.max(-10, Math.min(40, baseDayTemp + dayTempChange))));
            newDay.setNighttemp(String.valueOf(Math.max(-10, Math.min(40, baseNightTemp + nightTempChange))));
        }
        catch (NumberFormatException e)
        {
            newDay.setDaytemp(template.getDaytemp());
            newDay.setNighttemp(template.getNighttemp());
        }

        // 风向风力：大部分情况下保持不变
        String[] windDirections = {"东", "南", "西", "北", "东南", "东北", "西南", "西北"};
        String[] windPowers = {"1-2级", "3-4级", "4-5级", "5-6级"};

        if (random.nextFloat() < 0.8f)
        {
            newDay.setDaywind(template.getDaywind());
            newDay.setNightwind(template.getNightwind());
            newDay.setDaypower(template.getDaypower());
            newDay.setNightpower(template.getNightpower());
        }
        else
        {
            int dirIndex = random.nextInt(windDirections.length);
            int powerIndex = random.nextInt(windPowers.length);

            newDay.setDaywind(windDirections[dirIndex]);
            newDay.setNightwind(windDirections[(dirIndex + 1) % windDirections.length]);
            newDay.setDaypower(windPowers[powerIndex]);
            newDay.setNightpower(windPowers[powerIndex]);
        }

        // 湿度：50-80%之间随机
        int humidity = 50 + random.nextInt(31);
        newDay.setDayhumidity(String.valueOf(humidity));
        newDay.setNighthumidity(String.valueOf(humidity + random.nextInt(11) - 5));

        return newDay;
    }

    /**
     * 计算下一天的星期
     */
    private String calculateNextWeek(String currentWeek, int daysAfter)
    {
        // 星期映射
        Map<String, Integer> weekMap = new HashMap<>();
        weekMap.put("星期日", 0);
        weekMap.put("星期一", 1);
        weekMap.put("星期二", 2);
        weekMap.put("星期三", 3);
        weekMap.put("星期四", 4);
        weekMap.put("星期五", 5);
        weekMap.put("星期六", 6);

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        Integer currentIndex = weekMap.get(currentWeek);
        if (currentIndex == null)
        {
            currentIndex = 0;
        }

        int newIndex = (currentIndex + daysAfter) % 7;
        return weekDays[newIndex];
    }
}