package com.hmproductions.merryweather.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hmproductions.merryweather.R;
import com.hmproductions.merryweather.data.Weather;
import com.hmproductions.merryweather.connectors.WeatherLoader;
import com.hmproductions.merryweather.utils.AnimationUtils;
import com.hmproductions.merryweather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Weather>> {

    // Defining constants
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String WEATHER_API_APP_ID = "&appid=8ab04133be67ec8fda5053218b3fc48d";
    public static final String DEGREE = "\u00B0";
    private static final int LOADER_ID = 101;
    private static final String LOG_TAG = ":::";
    static final String CITY_KEY = "city-key";

    RelativeLayout main_layout, connection_error_layout;

    EditText city_editText;
    ImageView weather_imageView;
    TextView city_textView, weather_textView, description_textView, date_textView;
    TextView minTemp_textView, maxTemp_textView, humidity_textView;
    TextView windSpeed_textView, windDegree_textView;
    Button searchButton;
    ProgressBar progressBar;

    // Variables
    public static String mCity;
    private boolean mConnection;

    public static List<Weather> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindViews();

        SearchButtonClickListener();

        // Checks if Internet Connectivity is Available and sets mConnection to true otherwise false
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        mConnection = (networkInfo != null && networkInfo.isConnected());
        if(!mConnection)
        {
            main_layout.setVisibility(View.INVISIBLE);
            connection_error_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            main_layout.setVisibility(View.VISIBLE);
            connection_error_layout.setVisibility(View.INVISIBLE);
        }
    }

    private void BindViews()
    {
        // Binding all the views
        main_layout = (RelativeLayout)findViewById(R.id.main_layout);
        connection_error_layout = (RelativeLayout)findViewById(R.id.connection_error_layout);

        city_editText = (EditText)findViewById(R.id.city_editText);
        weather_imageView = (ImageView)findViewById(R.id.weather_imageView);
        searchButton = (Button)findViewById(R.id.search_button);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        date_textView = (TextView)findViewById(R.id.date_textView);
        city_textView = (TextView)findViewById(R.id.city_textView);
        weather_textView = (TextView)findViewById(R.id.weather_textView);
        description_textView = (TextView)findViewById(R.id.description_textView);

        minTemp_textView = (TextView)findViewById(R.id.minTemp_textView);
        maxTemp_textView = (TextView)findViewById(R.id.maxTemp_textView);
        humidity_textView = (TextView)findViewById(R.id.humidity_textView);
        windSpeed_textView = (TextView)findViewById(R.id.windSpeed_textView);
        windDegree_textView = (TextView)findViewById(R.id.windDegree_textView);
    }

    private void SearchButtonClickListener()
    {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AnimationUtils.AnimateCircularReveal(view);

                mCity = city_editText.getText().toString();
                if(mCity.isEmpty() || mCity.equals(""))
                    Toast.makeText(MainActivity.this,"Please enter city name", Toast.LENGTH_SHORT).show();
                else
                {
                    LoaderManager loaderManager = getSupportLoaderManager();
                    loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
                }
            }
        });
    }

    private void updateUI(List<Weather> data)
    {
        if(data == null)
            Toast.makeText(MainActivity.this,"Invalid city name", Toast.LENGTH_SHORT).show();
        else
        {
            mData = data;
            Weather currentWeather = data.get(0);

            city_textView.setText(currentWeather.getCity());
            description_textView.setText(currentWeather.getDescription());
            weather_textView.setText(currentWeather.getWeather());
            date_textView.setText(currentWeather.getDate());

            minTemp_textView.setText("Min. Temp :" + String.valueOf(currentWeather.getMin_temperature() -273) + DEGREE + "C");
            maxTemp_textView.setText("Max. Temp :" + String.valueOf(currentWeather.getMax_temperature() -273) + DEGREE + "C");
            windSpeed_textView.setText("Wind Speed :" + String.valueOf((int)currentWeather.getWind_speed()) + " kph");
            humidity_textView.setText("Humidity :" + String.valueOf(currentWeather.getHumidity()) + "%");
            windDegree_textView.setText("Degree :" + String.valueOf(currentWeather.getDegree()) + DEGREE);

            WeatherUtils.setWeatherImage(weather_imageView, currentWeather.getWeather());
        }
    }

    @Override
    public Loader<List<Weather>> onCreateLoader(int id, Bundle args) {
        String url = WEATHER_API_URL + mCity.toLowerCase() + WEATHER_API_APP_ID;
        progressBar.setVisibility(View.VISIBLE);
        Log.v(LOG_TAG, url);
        return new WeatherLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> data) {
        updateUI(data);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Weather>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!mConnection)
            menu.findItem(R.id.forecast_item).setVisible(false);
        else
            menu.findItem(R.id.forecast_item).setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.forecast_item :
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra(CITY_KEY,mCity);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
