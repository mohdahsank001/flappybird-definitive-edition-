package com.example.game;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Coordinate;

import org.json.JSONException;
import org.json.JSONObject;

public class AndroidWeatherInterface implements WeatherInterface {

    private String api_key;

    private String result;

    public AndroidWeatherInterface() {
        api_key = "e303d65848bb20ffb2e6fe228921815f";
    }


    // This function gets the weather using the OpenWeatherMap api along with location information
    //  Latitude and Longitude
    @Override
    public String getWeather(double lat, double lon) {

        int weather_id = 0;

        // Retrieve weather API result.
        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(api_key);
        result = openWeatherClient.currentWeather().single().byCoordinate(Coordinate.of(lat, lon))
                .language(Language.ENGLISH).unitSystem(UnitSystem.STANDARD).retrieve().asJSON();

        JSONObject root = null;
        try {
            root = new JSONObject(result);
            JSONObject jsonObject = root.getJSONArray("weather").getJSONObject(0);
            weather_id = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Use the unique weather id to determine the actual weather.
        // e.g. id 802 is scattered clouds, considered as clouds.
        // e.g. id 500 is light rain and is considered as rains.
        if (weather_id > 200 && weather_id < 600){
            return "rain";
        }
        if (weather_id >= 600 && weather_id < 800){
            return "snow";
        }
        if (weather_id == 800){
            return "sunny";
        }
        if (weather_id > 800){
            return "cloudy";
        }
        return "normal";
    }

    // Tell the game which background to render by returning the background image file name.
    public String setBackground(String weather){
        switch (weather){
            case "rain":
                return "bg_rain.png";
            case "snow":
                return "bg_snow.png";
            case "sunny":
                return "bg_sunny.png";
            case "cloudy":
                return "bg_cloudy.png";
            default:
                return "bg.png";
        }
    }
}