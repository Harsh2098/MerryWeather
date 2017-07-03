package com.hmproductions.merryweather.connectors;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmproductions.merryweather.R;
import com.hmproductions.merryweather.data.Weather;
import com.hmproductions.merryweather.ui.MainActivity;
import com.hmproductions.merryweather.utils.WeatherUtils;

import java.util.List;

/**
 * Created by Harsh Mahajan on 3/7/2017.
 */

public class ForecastAdapter extends ArrayAdapter<Weather> {

    private List<Weather> mData;
    private Context mContext;

    public ForecastAdapter(@NonNull Context context, @LayoutRes int resource, List<Weather> data) {
        super(context, resource);
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Weather currentWeather = mData.get(position);

        if(convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        TextView weather_textView = convertView.findViewById(R.id.item_weather);
        TextView date_textView = convertView.findViewById(R.id.item_date);
        TextView description_textView = convertView.findViewById(R.id.item_description);
        TextView temp_textView = convertView.findViewById(R.id.item_temperature);
        ImageView weather_imageView = convertView.findViewById(R.id.item_image);

        description_textView.setText(currentWeather.getDescription());
        weather_textView.setText(currentWeather.getWeather());
        date_textView.setText(currentWeather.getDate());
        temp_textView.setText(String.valueOf(currentWeather.getMax_temperature() - 273) + MainActivity.DEGREE + "C");

        WeatherUtils.setWeatherImage(weather_imageView, currentWeather.getWeather());

        return convertView;
    }
}
