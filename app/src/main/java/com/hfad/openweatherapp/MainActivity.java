package com.hfad.openweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Weather weather;
    TextView textView;
    String city = "Townsville";
    String weatherFormat = "metric";
    String api = "497c54db9e77b4b34a094c92658d9d20";
    String urlTemplate = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&APPID=%s";
    String requestUrl;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        weather = new Weather();
        requestWeather();
    }

    private void requestWeather() {
        queue = Volley.newRequestQueue(this);
        requestUrl = String.format(urlTemplate, city, weatherFormat, api);
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
        tempValue.setText(weather.getTemp());
        humidValue.setText(weather.getHumidity());
        descriptionValue.setText(weather.getDescription());
        windValue.setText(weather.getWindSpeed());
    }

}
