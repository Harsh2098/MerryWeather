package com.hmproductions.merryweather.data;

/**
 * Created by Harsh Mahajan on 2/7/2017.
 */

public class Weather {

    private int min_temperature, max_temperature, humidity;
    private double wind_speed, degree;
    private String weather, description, date, city;

    public Weather(int min_temperature, int max_temperature, int humidity,
                   double wind_speed, double degree,
                   String weather, String description, String date, String city)
    {
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.degree = degree;
        this.weather = weather;
        this.description = description;
        this.date = date;
        this.city = city;
    }

    public int getMin_temperature() {
        return min_temperature;
    }

    public int getMax_temperature() {
        return max_temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public double getDegree() {
        return degree;
    }

    public String getWeather() {
        return weather;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }
}
