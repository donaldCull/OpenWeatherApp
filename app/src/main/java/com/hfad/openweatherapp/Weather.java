package com.hfad.openweatherapp;

public class Weather {
    private String city,temp, description, humidity, windSpeed;

    public Weather(){

    }

    public Weather(String jsonCity, String jsonTemp, String jsonDescription, String jsonHumidity, String jsonWindSpeed){
        city = jsonCity;
        temp = jsonTemp;
        description = jsonDescription;
        humidity = jsonHumidity;
        windSpeed = jsonWindSpeed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
