package com.example.game;

public interface WeatherInterface {
    public String getWeather(double lat, double lon);

    public String setBackground(String weather);
}
