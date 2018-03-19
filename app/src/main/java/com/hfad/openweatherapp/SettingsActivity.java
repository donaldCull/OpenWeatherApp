package com.hfad.openweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {
    public static final String USER_PREFERENCES = "userPrefs" ;
    public static final String City = "cityKey";
    public static final String Format = "formatKey";
    public static final String Lang = "langKey";

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedpreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);

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

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(City, selectedCity);
        editor.putString(Format, selectedFormat);
        editor.putString(Lang, selectedLang);
        editor.apply();

        System.out.printf("%s\n%s\n%s\n", selectedCity, selectedFormat, selectedLang);
    }
}
