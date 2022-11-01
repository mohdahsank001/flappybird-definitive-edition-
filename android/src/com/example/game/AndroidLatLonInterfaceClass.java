package com.example.game;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.os.Bundle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.badlogic.gdx.graphics.FPSLogger;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.lang.String;
import java.util.concurrent.TimeUnit;

public class AndroidLatLonInterfaceClass implements LatLonInterface{

    FusedLocationProviderClient FLPC;
    Activity context;

    int PERMISSION_ID = 44;

    public double latitude;
    public double longitude;

    public AndroidLatLonInterfaceClass(Activity context){
        this.context = context;

        context.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                FLPC = LocationServices.getFusedLocationProviderClient(context);

                getLastLocation();
            }
        });


        //

    }

    public void getLastLocation(){
        if (checkPermission()){
            if (isLocationEnabled()){
                FLPC.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null){
                            requestNewLocationData();
                        }
                        else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Toast.makeText(context.getApplicationContext(), String.valueOf(latitude), Toast.LENGTH_SHORT).show();
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context.getApplicationContext(), String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(context.getApplicationContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(context, intent, null);
            }
        }
        else {
            requestPermissions();
        }
    }

    public boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

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

    public void requestPermissions(){
        ActivityCompat.requestPermissions(context, new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

}
