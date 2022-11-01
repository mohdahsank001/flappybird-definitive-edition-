package com.example.game;

public interface LatLonInterface {

    public void getLastLocation();

    public boolean checkPermission();

    public boolean isLocationEnabled();

    public void requestNewLocationData();

    public void requestPermissions();

    public double getLatitude();

    public double getLongitude();
}
