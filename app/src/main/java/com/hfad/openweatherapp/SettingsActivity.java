package com.hfad.openweatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onPressReturn(View view){
        getUserPreferences();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getUserPreferences(){
        Spinner city = findViewById(R.id.locSpinner);
        Spinner format = findViewById(R.id.formatSpinner);
        Spinner language = findViewById(R.id.langSpinner);

        String selectedCity = String.valueOf(city.getSelectedItem());
        String selectedFormat = String.valueOf(format.getSelectedItem());
        String selectedLang = String.valueOf(language.getSelectedItem());

        System.out.printf("%s\n%s\n%s\n", selectedCity, selectedFormat, selectedLang);
    }
}
