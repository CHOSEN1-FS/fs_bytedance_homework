package com.example.day3.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.day3.R;
import com.example.day3.adapter.WeatherPagerAdapter;
import com.example.day3.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity
{
    private ViewPager2 viewPager;
    private Button btnCity, btnForecast;
    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupViewPager();
        setupButtonListeners();
        setupViewModel();
    }

    private void initViews()
    {
        viewPager = findViewById(R.id.viewPager);
        btnCity = findViewById(R.id.btn_city);
        btnForecast = findViewById(R.id.btn_forecast);

        // 设置按钮文字
        btnCity.setText("城市");
        btnForecast.setText("预测");
    }

    private void setupViewPager()
    {
        try
        {
            WeatherPagerAdapter adapter = new WeatherPagerAdapter(this);
            viewPager.setAdapter(adapter);

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
            {
                @Override
                public void onPageSelected(int position)
                {
                    updateButtonState(position);
                }
            });

            viewPager.setOffscreenPageLimit(1);

        }
        catch (Exception e)
        {
            Toast.makeText(this, "ViewPager初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupButtonListeners()
    {
        // 城市按钮 - 切换到当前天气页面（页面1）
        btnCity.setOnClickListener(v ->
        {
            try
            {
                viewPager.setCurrentItem(0, true);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "切换页面失败", Toast.LENGTH_SHORT).show();
            }
        });

        // 预测按钮 - 切换到预测页面（页面2）
        btnForecast.setOnClickListener(v ->
        {
            try
            {
                viewPager.setCurrentItem(1, true);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "切换页面失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewModel()
    {
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
    }

    private void updateButtonState(int position)
    {
        try
        {
            if (position == 0)
            {
                // 当前天气页选中
                btnCity.setTextColor(Color.parseColor("#007AFF"));
                btnCity.setBackgroundResource(R.drawable.btn_city_selected);
                btnForecast.setTextColor(Color.parseColor("#666666"));
                btnForecast.setBackgroundResource(R.drawable.btn_forecast_normal);
            }
            else
            {
                // 预测页选中
                btnCity.setTextColor(Color.parseColor("#666666"));
                btnCity.setBackgroundResource(R.drawable.btn_city_normal);
                btnForecast.setTextColor(Color.parseColor("#007AFF"));
                btnForecast.setBackgroundResource(R.drawable.btn_forecast_selected);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}