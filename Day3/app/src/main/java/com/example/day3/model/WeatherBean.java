package com.example.day3.model;

import java.util.List;

public class WeatherBean
{
    /**
     * status : 1
     * count : 1
     * info : OK
     * infocode : 10000
     * lives : [{"province":"江苏","city":"南京市","adcode":"320100","weather":"阴","temperature":"17","winddirection":"南","windpower":"≤3","humidity":"41","reporttime":"2025-12-10 15:01:45","temperature_float":"17.0","humidity_float":"41.0"}]
     * forecasts : [{"city":"南京市","adcode":"320100","province":"江苏","reporttime":"2025-12-10 15:01:45","casts":[{"date":"2025-12-10","week":"星期三","dayweather":"阴","nightweather":"多云","daytemp":"17","nighttemp":"8","daywind":"南","nightwind":"南","daypower":"≤3","nightpower":"≤3"},{"date":"2025-12-11","week":"星期四","dayweather":"晴","nightweather":"晴","daytemp":"20","nighttemp":"9","daywind":"东南","nightwind":"东南","daypower":"≤3","nightpower":"≤3"}]}]
     */

    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<LivesBean> lives;
    private List<ForecastsBean> forecasts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<LivesBean> getLives() {
        return lives;
    }

    public void setLives(List<LivesBean> lives) {
        this.lives = lives;
    }

    public List<ForecastsBean> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastsBean> forecasts) {
        this.forecasts = forecasts;
    }

    public static class LivesBean
    {
        /**
         * province : 江苏
         * city : 南京市
         * adcode : 320100
         * weather : 阴
         * temperature : 17
         * winddirection : 南
         * windpower : ≤3
         * humidity : 41
         * reporttime : 2025-12-10 15:01:45
         * temperature_float : 17.0
         * humidity_float : 41.0
         */

        private String province;
        private String city;
        private String adcode;
        private String weather;
        private String temperature;
        private String winddirection;
        private String windpower;
        private String humidity;
        private String reporttime;
        private String temperature_float;
        private String humidity_float;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWinddirection() {
            return winddirection;
        }

        public void setWinddirection(String winddirection) {
            this.winddirection = winddirection;
        }

        public String getWindpower() {
            return windpower;
        }

        public void setWindpower(String windpower) {
            this.windpower = windpower;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getReporttime() {
            return reporttime;
        }

        public void setReporttime(String reporttime) {
            this.reporttime = reporttime;
        }

        public String getTemperature_float() {
            return temperature_float;
        }

        public void setTemperature_float(String temperature_float) {
            this.temperature_float = temperature_float;
        }

        public String getHumidity_float() {
            return humidity_float;
        }

        public void setHumidity_float(String humidity_float) {
            this.humidity_float = humidity_float;
        }
    }

    public static class ForecastsBean
    {
        /**
         * city : 南京市
         * adcode : 320100
         * province : 江苏
         * reporttime : 2025-12-10 15:01:45
         * casts : [{"date":"2025-12-10","week":"星期三","dayweather":"阴","nightweather":"多云","daytemp":"17","nighttemp":"8","daywind":"南","nightwind":"南","daypower":"≤3","nightpower":"≤3"},{"date":"2025-12-11","week":"星期四","dayweather":"晴","nightweather":"晴","daytemp":"20","nighttemp":"9","daywind":"东南","nightwind":"东南","daypower":"≤3","nightpower":"≤3"}]
         */

        private String city;
        private String adcode;
        private String province;
        private String reporttime;
        private List<CastsBean> casts;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReporttime() {
            return reporttime;
        }

        public void setReporttime(String reporttime) {
            this.reporttime = reporttime;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public static class CastsBean
        {
            /**
             * date : 2025-12-10
             * week : 星期三
             * dayweather : 阴
             * nightweather : 多云
             * daytemp : 17
             * nighttemp : 8
             * daywind : 南
             * nightwind : 南
             * daypower : ≤3
             * nightpower : ≤3
             * dayhumidity : 50
             * nighthumidity : 60
             */

            private String date;
            private String week;
            private String dayweather;
            private String nightweather;
            private String daytemp;
            private String nighttemp;
            private String daywind;
            private String nightwind;
            private String daypower;
            private String nightpower;
            private String dayhumidity;
            private String nighthumidity;

            public String getDate()
            {
                return date;
            }

            public void setDate(String date)
            {
                this.date = date;
            }

            public String getWeek()
            {
                return week;
            }

            public void setWeek(String week)
            {
                this.week = week;
            }

            public String getDayweather()
            {
                return dayweather;
            }

            public void setDayweather(String dayweather)
            {
                this.dayweather = dayweather;
            }

            public String getNightweather()
            {
                return nightweather;
            }

            public void setNightweather(String nightweather)
            {
                this.nightweather = nightweather;
            }

            public String getDaytemp()
            {
                return daytemp;
            }

            public void setDaytemp(String daytemp)
            {
                this.daytemp = daytemp;
            }

            public String getNighttemp()
            {
                return nighttemp;
            }

            public void setNighttemp(String nighttemp)
            {
                this.nighttemp = nighttemp;
            }

            public String getDaywind()
            {
                return daywind;
            }

            public void setDaywind(String daywind)
            {
                this.daywind = daywind;
            }

            public String getNightwind()
            {
                return nightwind;
            }

            public void setNightwind(String nightwind)
            {
                this.nightwind = nightwind;
            }

            public String getDaypower()
            {
                return daypower;
            }

            public void setDaypower(String daypower)
            {
                this.daypower = daypower;
            }

            public String getNightpower()
            {
                return nightpower;
            }

            public void setNightpower(String nightpower)
            {
                this.nightpower = nightpower;
            }

            public String getDayhumidity()
            {
                return dayhumidity;
            }

            public void setDayhumidity(String dayhumidity)
            {
                this.dayhumidity = dayhumidity;
            }

            public String getNighthumidity()
            {
                return nighthumidity;
            }

            public void setNighthumidity(String nighthumidity)
            {
                this.nighthumidity = nighthumidity;
            }
        }
    }
}