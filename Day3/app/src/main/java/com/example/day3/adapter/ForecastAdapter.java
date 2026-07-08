package com.example.day3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.day3.R;
import com.example.day3.model.WeatherBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>
{

    private List<WeatherBean.ForecastsBean.CastsBean> forecastList;

    public ForecastAdapter(List<WeatherBean.ForecastsBean.CastsBean> forecastList)
    {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if (forecastList == null || position >= forecastList.size())
        {
            return;
        }

        WeatherBean.ForecastsBean.CastsBean forecast = forecastList.get(position);

        // 1. 获取日期并格式化
        String dateStr = forecast.getDate();
        String formattedDate = formatDate(dateStr); // 格式化为"12-12"

        // 2. 根据位置确定日期标签
        String dayLabel = getDayLabel(position, dateStr);

        // 3. 分别设置到两个TextView
        holder.tvDate.setText(formattedDate);        // 设置日期（12-12）
        holder.tvWeek.setText(dayLabel);            // 设置"今天"/"明天"/星期几


        // 设置天气图标
        holder.ivWeather.setImageResource(getWeatherIcon(forecast.getDayweather()));

        // 设置天气描述
        holder.tvWeather.setText(forecast.getDayweather());

        // 设置温度范围（去掉"/"，改为空格分隔）
        holder.tvTempRange.setText(formatTemperature(forecast.getDaytemp(), forecast.getNighttemp()));
    }

    @Override
    public int getItemCount() {
        return forecastList == null ? 0 : forecastList.size();
    }

    public void updateData(List<WeatherBean.ForecastsBean.CastsBean> newForecastList)
    {
        this.forecastList = newForecastList;
        notifyDataSetChanged();
    }

    private String getDayLabel(int position, String dateStr) {
        // 如果是第一天
        if (position == 0) {
            return "今天";
        }
        // 如果是第二天
        else if (position == 1) {
            return "明天";
        }
        // 如果是第三天
        else if (position == 2) {
            return "后天";
        }
        // 其他情况，返回星期几
        else {
            return getWeekdayFromDate(dateStr);
        }
    }

    private String getWeekdayFromDate(String dateStr) {
        try {
            // 假设dateStr格式为"12-12"或"2023-12-12"
            SimpleDateFormat sdf;
            if (dateStr.contains("-") && dateStr.split("-").length == 3) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                // 如果没有年份，使用今年
                String year = new SimpleDateFormat("yyyy").format(new Date());
                dateStr = year + "-" + dateStr;
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            }

            Date date = sdf.parse(dateStr);
            if (date != null) {
                // 转换为星期几
                SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE", Locale.CHINA);
                String week = weekFormat.format(date);

                // 简化为中文星期
                return week.replace("星期", "周");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        // 如果包含完整日期，只提取月-日
        if (dateStr.contains("-") && dateStr.split("-").length == 3) {
            String[] parts = dateStr.split("-");
            if (parts.length >= 3) {
                return parts[1] + "-" + parts[2];
            }
        }

        return dateStr;
    }

    private String formatTemperature(String dayTemp, String nightTemp) {
        // 确保温度值有效
        if (dayTemp == null) dayTemp = "";
        if (nightTemp == null) nightTemp = "";

        // 去掉可能存在的°符号，然后重新添加
        dayTemp = dayTemp.replace("°", "").trim();
        nightTemp = nightTemp.replace("°", "").trim();

        return dayTemp + "° " + nightTemp + "°";
    }

    private int getWeatherIcon(String weather)
    {
        if (weather == null)
            return R.drawable.ic_sunny;
        switch (weather)
        {
            case "晴":
                return R.drawable.ic_sunny;
            case "多云":
                return R.drawable.ic_cloudy;
            case "小雨":
            case "中雨":
            case "大雨":
            case "暴雨":
                return R.drawable.ic_rain;
            case "小雪":
            case "中雪":
            case "大雪":
                return R.drawable.ic_snow;
            default:
                return R.drawable.ic_sunny;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvDate, tvWeek, tvWeather, tvTempRange;
        ImageView ivWeather;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvWeek = itemView.findViewById(R.id.tv_week);
            ivWeather = itemView.findViewById(R.id.iv_weather);
            tvWeather = itemView.findViewById(R.id.tv_weather);
            tvTempRange = itemView.findViewById(R.id.tv_temp_range);
        }
    }
}