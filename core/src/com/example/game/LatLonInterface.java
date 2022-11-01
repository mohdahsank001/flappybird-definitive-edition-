package com.example.game;

public interface LatLonInterface {

    void getLastLocation();

    boolean checkPermission();

    boolean isLocationEnabled();

    void requestNewLocationData();

    double getLatitude();

    double getLongitude();
}
