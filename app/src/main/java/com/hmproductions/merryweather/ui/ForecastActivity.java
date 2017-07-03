package com.hmproductions.merryweather.ui;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.hmproductions.merryweather.R;
import com.hmproductions.merryweather.connectors.ForecastAdapter;
import com.hmproductions.merryweather.data.Weather;

import java.util.List;

public class ForecastActivity extends AppCompatActivity {

    ListView forecastListView;
    List<Weather> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra(MainActivity.CITY_KEY).toUpperCase());

        forecastListView = (ListView)findViewById(R.id.forecast_list);

        data = MainActivity.mData;
        ForecastAdapter mAdapter = new ForecastAdapter(this, R.layout.list_item, data);

        forecastListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
