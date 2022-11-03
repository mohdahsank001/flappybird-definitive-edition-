package com.example.game;

// The Interface for calling methods from the Android module
// due to the imcompatibility of gdxlib and other libs.
public interface WeatherInterface {
    String getWeather(double lat, double lon);

    String setBackground(String weather);
}
