package com.hfad.openweatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    public static final String USER_PREFERENCES = "userPrefs" ;
    public static final String City = "cityKey";
    public static final String Format = "formatKey";
    public static final String Lang = "langKey";
    public static final String City_Index = "cityIndexKey";
    public static final String Format_Index = "formatIndexKey";
    public static final String Lang_Index = "langIndexKey";

    Spinner city;
    Spinner format;
    Spinner language;

    SharedPreferences sharedpreferences;
    private GestureDetectorCompat detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        city = findViewById(R.id.locSpinner);
        format = findViewById(R.id.formatSpinner);
        language = findViewById(R.id.langSpinner);

        sharedpreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);

        city.setSelection(sharedpreferences.getInt(City_Index, 0));
        format.setSelection(sharedpreferences.getInt(Format_Index, 0));
        language.setSelection(sharedpreferences.getInt(Lang_Index, 0));

        detector = new GestureDetectorCompat(this, this);

    }

    public void onPressReturn(View view){
        getUserPreferences();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getUserPreferences(){

        String selectedCity = String.valueOf(city.getSelectedItem());
        String selectedFormat = String.valueOf(format.getSelectedItem());
        String selectedLang = String.valueOf(language.getSelectedItem());

        int selectedCityIndex = city.getSelectedItemPosition();
        int selectedFormatIndex = format.getSelectedItemPosition();
        int selectedLangIndex = language.getSelectedItemPosition();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(City, selectedCity);
        editor.putString(Format, selectedFormat);
        editor.putString(Lang, selectedLang);

        editor.putInt(City_Index, selectedCityIndex);
        editor.putInt(Format_Index, selectedFormatIndex);
        editor.putInt(Lang_Index, selectedLangIndex);
        editor.apply();

        System.out.printf("%s\n%s\n%s\n", selectedCity, selectedFormat, selectedLang);
    }

    /////////////////// Gestures //////////////////////////

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
        // right to left
        if (endEvent.getX() < startEvent.getX()){
            getUserPreferences();
            Intent intent = new Intent(this, MainActivity.class);
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
