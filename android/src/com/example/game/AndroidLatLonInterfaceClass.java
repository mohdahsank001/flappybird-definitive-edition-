package com.example.game;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class AndroidLatLonInterfaceClass implements LatLonInterface{

    FusedLocationProviderClient FLPC;
    Activity context;

    double latitude;
    double longitude;

    public AndroidLatLonInterfaceClass(Activity context){
        this.context = context;

        context.runOnUiThread(new Runnable(){
            @Override
            public void run() {

                FLPC = LocationServices.getFusedLocationProviderClient(context);
            }
        });
    }

    // Get the current location of the device in latitude and longitude.
    public void getLastLocation(){

        // Check if we have the permission to use location information.
        if (checkPermission()){

            // Check if the GPS is enabled (on).
            if (isLocationEnabled()){
                FLPC.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

                    // On completing the task, update the latitude and longitude to the variable.
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null){
                            requestNewLocationData();
                        }
                        else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                });
            }
            else {
                context.runOnUiThread(new Runnable() {

                    // If permitted and the GPS is not on then,
                    // Pop out a Toast reminding the user to turn on the GPS.
                    @Override
                    public void run() {
                        Toast.makeText(context.getApplicationContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(context, intent, null);
                    }
                });
            }
        }
        else {
        }
    }

    // Check for location information permission for privacy issue.
    // Returns true if permitted, vice versa.
    public boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // Check for GPS, whether it's enabled or not.
    // return true if permitted, vice versa.
    public boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) context.
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @SuppressLint("MissingPermission")
    public void requestNewLocationData(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        FLPC = LocationServices.getFusedLocationProviderClient(context);
        FLPC.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    public LocationCallback locationCallback = new LocationCallback(){
        public void onLocationResult(LocationResult locationResult){
            Location lastLocation = locationResult.getLastLocation();
        }
    };

    // getter function for other module.
    public double getLatitude(){
        return latitude;
    }

    // getter function for other module.
    public double getLongitude(){
        return longitude;
    }
}
