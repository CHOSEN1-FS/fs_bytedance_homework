package com.example.day3.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.day3.fragment.CurrentWeatherFragment;
import com.example.day3.fragment.ForecastFragment;

public class WeatherPagerAdapter extends FragmentStateAdapter
{
    public WeatherPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch (position)
        {
            case 0:
                return new CurrentWeatherFragment();
            case 1:
                return new ForecastFragment();
            default:
                return new CurrentWeatherFragment();
        }
    }

    @Override
    public int getItemCount()
    {
        return 2; // 两个页面：当前天气 + 7天预测
    }
}