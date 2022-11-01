package com.example.game;

import com.badlogic.gdx.graphics.Texture;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Coordinate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroidWeatherInterface implements WeatherInterface {

    private String api_key;
    private String url;

    private String result;

    public AndroidWeatherInterface() {
        api_key = "e303d65848bb20ffb2e6fe228921815f";
    }

    @Override
    public String getWeather(double lat, double lon) {

        int weather_id = 0;

        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(api_key);
        result = openWeatherClient.currentWeather().single().byCoordinate(Coordinate.of(lat, lon))
                .language(Language.ENGLISH).unitSystem(UnitSystem.STANDARD).retrieve().asJSON();

        System.out.println(result);

        JSONObject root = null;
        try {
            root = new JSONObject(result);
            JSONObject jsonarray = root.getJSONArray("weather").getJSONObject(0);
            weather_id = jsonarray.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

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