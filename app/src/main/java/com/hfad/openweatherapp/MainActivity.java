package com.hfad.openweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    public static final String USER_PREFERENCES = "userPrefs" ;
    ConstraintLayout layout;
    SharedPreferences sharedpreferences;
    public static final String City = "cityKey";
    public static final String Format = "formatKey";
    public static final String Lang = "langKey";
    Weather weather;
    String city = "Townsville";
    String weatherFormat = "metric";
    String language = "en";
    String metricWindSpeed = "mps", imperialWindSpeed = "mph";
    String api = "enter your api key here";
    String urlTemplate = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&lang=%s&APPID=%s";
    String requestUrl;
    RequestQueue queue;
    private GestureDetectorCompat detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        city = sharedpreferences.getString(City, "Townsville");
        weatherFormat = sharedpreferences.getString(Format, "metric");
        language = sharedpreferences.getString(Lang, "en");
        weather = new Weather();
        requestWeather();
        layout = findViewById(R.id.constraintLayout);
        detector = new GestureDetectorCompat(this, this);
    }

    public void onPressSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void requestWeather() {
        queue = Volley.newRequestQueue(this);
        requestUrl = String.format(urlTemplate, city, weatherFormat, language, api);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        extractWeatherData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void extractWeatherData(JSONObject response) {
        try {
            JSONObject main = response.getJSONObject("main");
            weather.setTemp(main.getString("temp"));
            weather.setHumidity(main.getString("humidity"));

            JSONArray jArr = response.getJSONArray("weather");
            JSONObject weatherDescription = jArr.getJSONObject(0);
            weather.setDescription(weatherDescription.getString("description"));

            JSONObject windDescription = response.getJSONObject("wind");
            weather.setWindSpeed(windDescription.getString("speed"));

            weather.setCity(response.getString("name"));
            insertWeatherData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertWeatherData(){
        TextView cityLabel = findViewById(R.id.cityName);
        TextView tempValue = findViewById(R.id.tempValue);
        TextView humidValue = findViewById(R.id.humidityValue);
        TextView descriptionValue = findViewById(R.id.descriptionValue);
        TextView windValue = findViewById(R.id.windSpeedValue);

        cityLabel.setText(weather.getCity());
        // todo change temperature format based on selection
        tempValue.setText(getString(R.string.tempValue, weather.getTemp()));
        humidValue.setText(getString(R.string.humidityValue, weather.getHumidity(), "%"));
        descriptionValue.setText(weather.getDescription());

        // change the speed measurement format
        if (weatherFormat.equals("Metric")) {
            windValue.setText(getString(R.string.windspeedValue, weather.getWindSpeed(), metricWindSpeed));
        }
        else {
            windValue.setText(getString(R.string.windspeedValue, weather.getWindSpeed(), imperialWindSpeed));
        }
    }

    ////////////////////////////// Gestures /////////////////////////////////////

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent startEvent, MotionEvent endEvent, float v, float v1) {
        // left to right
        if (endEvent.getX() > startEvent.getX()){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
