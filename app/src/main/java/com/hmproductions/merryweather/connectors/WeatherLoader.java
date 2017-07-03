package com.hmproductions.merryweather.connectors;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.hmproductions.merryweather.data.Weather;
import com.hmproductions.merryweather.utils.WeatherUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Harsh Mahajan on 2/7/2017.
 */

public class WeatherLoader extends AsyncTaskLoader<List<Weather>> {

    private static final String LOG_TAG = WeatherLoader.class.getSimpleName();
    private String mURLString;

    public WeatherLoader(Context context, String URLString) {
        super(context);
        mURLString = URLString;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Weather> loadInBackground() {

        String jsonResponse = "";

        URL url = createURL(mURLString);

        if (url != null)
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return WeatherUtils.extractFromJSONResponse(jsonResponse);
    }

    private URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonString = "";

        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            inputStream = connection.getInputStream();
            jsonString = parseInputString(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error response code: " + connection.getResponseCode());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }

        return jsonString;
    }

    private String parseInputString(InputStream inputStream) throws IOException
    {
        StringBuilder builder = new StringBuilder();

        if(inputStream !=null) {
            InputStreamReader reader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            while (line != null)
            {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return builder.toString();
    }
}
