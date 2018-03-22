package com.hfad.openweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    public static final String USER_PREFERENCES = "userPrefs" ;
    SharedPreferences sharedpreferences;
    public static final String City = "cityKey";
    public static final String Format = "formatKey";
    public static final String Lang = "langKey";

    Weather weather;
    TextView textView; //possibly redundant
    String city = "Townsville";
    String weatherFormat = "metric";
    String language = "en";
    String api = "497c54db9e77b4b34a094c92658d9d20";
    String urlTemplate = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&lang=%s&APPID=%s";
    String requestUrl;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        city = sharedpreferences.getString(City, "Townsville");
        weatherFormat = sharedpreferences.getString(Format, "metric");
        language = sharedpreferences.getString(Lang, "en");

        textView = findViewById(R.id.text);
        weather = new Weather();
        requestWeather();
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
// todo change wind speed abbreviation if the number format is metre per second mps or imperial mph mile per hour
        TextView cityLabel = findViewById(R.id.cityName);
        TextView tempValue = findViewById(R.id.tempValue);
        TextView humidValue = findViewById(R.id.humidityValue);
        TextView descriptionValue = findViewById(R.id.descriptionValue);
        TextView windValue = findViewById(R.id.windSpeedValue);

        cityLabel.setText(weather.getCity());
        tempValue.setText(getString(R.string.tempValue, weather.getTemp()));
        humidValue.setText(getString(R.string.humidityValue, weather.getHumidity(), "%"));
        descriptionValue.setText(weather.getDescription());
        windValue.setText(weather.getWindSpeed());
    }

}
