package com.example.game;

// The interface for calling methods from the Android module
// due to the imcompatibility of gdxlib and other libs.
public interface LatLonInterface {

    void getLastLocation();

    boolean checkPermission();

    boolean isLocationEnabled();

    void requestNewLocationData();

    double getLatitude();

    double getLongitude();
}
