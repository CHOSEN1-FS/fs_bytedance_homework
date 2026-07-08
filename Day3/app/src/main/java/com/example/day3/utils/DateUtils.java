package com.example.day3.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils
{
    // 将日期字符串转换为Calendar
    public static Calendar getCalendarFromString(String dateStr)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Calendar.getInstance();
        }
    }

    // 获取星期几的中文表示
    public static String getChineseWeek(Calendar calendar)
    {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek)
        {
            case Calendar.SUNDAY: return "周日";
            case Calendar.MONDAY: return "周一";
            case Calendar.TUESDAY: return "周二";
            case Calendar.WEDNESDAY: return "周三";
            case Calendar.THURSDAY: return "周四";
            case Calendar.FRIDAY: return "周五";
            case Calendar.SATURDAY: return "周六";
            default: return "";
        }
    }

    // 获取星期几的完整中文表示
    public static String getFullChineseWeek(Calendar calendar)
    {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek)
        {
            case Calendar.SUNDAY: return "星期日";
            case Calendar.MONDAY: return "星期一";
            case Calendar.TUESDAY: return "星期二";
            case Calendar.WEDNESDAY: return "星期三";
            case Calendar.THURSDAY: return "星期四";
            case Calendar.FRIDAY: return "星期五";
            case Calendar.SATURDAY: return "星期六";
            default: return "";
        }
    }

    // 格式化日期为"MM月dd日"
    public static String formatMonthDay(Calendar calendar)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    // 格式化日期为"yyyy-MM-dd"
    public static String formatDate(Calendar calendar)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    // 在指定日期上增加天数
    public static Calendar addDays(Calendar calendar, int days)
    {
        Calendar newCalendar = (Calendar) calendar.clone();
        newCalendar.add(Calendar.DAY_OF_MONTH, days);
        return newCalendar;
    }

    // 获取今天是星期几
    public static String getTodayWeek()
    {
        Calendar calendar = Calendar.getInstance();
        return getChineseWeek(calendar);
    }

    // 获取今天是几月几日
    public static String getTodayMonthDay()
    {
        Calendar calendar = Calendar.getInstance();
        return formatMonthDay(calendar);
    }

    // 获取今天日期
    public static String getTodayDate()
    {
        Calendar calendar = Calendar.getInstance();
        return formatDate(calendar);
    }
}