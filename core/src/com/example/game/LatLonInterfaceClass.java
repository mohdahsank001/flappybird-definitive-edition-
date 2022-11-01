package com.example.game;

public class LatLonInterfaceClass implements LatLonInterface{
    @Override
    public void getLastLocation() {

    }

    @Override
    public boolean checkPermission() {
        return false;
    }

    @Override
    public boolean isLocationEnabled() {
        return false;
    }

    @Override
    public void requestNewLocationData() {

    }

    @Override
    public void requestPermissions() {

    }

    @Override
    public double getLatitude() {
        return 0;
    }

    @Override
    public double getLongitude() {
        return 0;
    }
}
