package com.hfad.openweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String city = "Townsville";
    String weatherFormat = "metric";
    String api = "497c54db9e77b4b34a094c92658d9d20";
    String urlTemplate = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&APPID=%s";
    String requestUrl;
    // Instantiate the RequestQueue.
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWeather();
    }

    private void requestWeather() {
        queue = Volley.newRequestQueue(this);
        textView = findViewById(R.id.text);
        requestUrl = String.format(urlTemplate, city, weatherFormat, api);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }
}
