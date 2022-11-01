package com.example.game;

public interface WeatherInterface {
    String getWeather(double lat, double lon);

    String setBackground(String weather);
}
