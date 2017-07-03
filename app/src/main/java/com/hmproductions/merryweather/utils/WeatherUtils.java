package com.hmproductions.merryweather.utils;

import android.widget.ImageView;

import com.hmproductions.merryweather.ui.MainActivity;
import com.hmproductions.merryweather.R;
import com.hmproductions.merryweather.data.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh Mahajan on 3/7/2017.
 */

public class WeatherUtils {

    private static final CharSequence SEARCH_BY_TIME = "06:00:00";
    private static List<Weather> data = new ArrayList<>();

    private static int min_temperature, max_temperature, humidity;
    private static double wind_speed, degree;
    private static String weather, description, date, city;

    public static List<Weather> extractFromJSONResponse(String jsonResponse)
    {
        if(jsonResponse == null || jsonResponse.isEmpty() || jsonResponse.equals(""))
            return null;

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray list = root.getJSONArray("list");
            data.clear();

            for (int i=0 ; i<list.length() ; ++i)
            {
                JSONObject currentListItem = list.getJSONObject(i);
                String date_text = currentListItem.getString("dt_txt");

                if(date_text.contains(SEARCH_BY_TIME))
                {
                    JSONObject main = currentListItem.getJSONObject("main");
                    min_temperature = (int) main.getDouble("temp_min");
                    max_temperature = (int) main.getDouble("temp_max");
                    humidity = main.getInt("humidity");

                    JSONObject wind = currentListItem.getJSONObject("wind");
                    wind_speed = wind.getDouble("speed");
                    degree = wind.getDouble("deg");

                    JSONArray weatherArray = currentListItem.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    weather = weatherObject.getString("main");
                    description = weatherObject.getString("description");
                    city = MainActivity.mCity + ", " + root.getJSONObject("city").getString("country");

                    date = date_text.substring(0,10);
                    description.substring(0,1).toUpperCase();

                    data.add(new Weather(min_temperature, max_temperature, humidity, wind_speed, degree, weather, description, date, city));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void setWeatherImage(ImageView weather_imageView, String weather) {

        if(weather.equalsIgnoreCase("rain"))
            weather_imageView.setImageResource(R.drawable.ic_rain);
        else if(weather.equalsIgnoreCase("clouds"))
            weather_imageView.setImageResource(R.drawable.ic_cloudy);
        else if(weather.equalsIgnoreCase("clear"))
            weather_imageView.setImageResource(R.drawable.ic_clear);
        else if(weather.equalsIgnoreCase("snow"))
            weather_imageView.setImageResource(R.drawable.ic_snow);
        else if(weather.equalsIgnoreCase("thunderstorm"))
            weather_imageView.setImageResource(R.drawable.ic_storm);
        else
            weather_imageView.setImageResource(R.drawable.ic_fog);
    }
}
