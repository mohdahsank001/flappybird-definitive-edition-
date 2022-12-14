package com.example.game;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.InputDevice;
import android.widget.Toast;

import java.util.ArrayList;

public class AndroidInterfaceBluetoothClass implements BluetoothInterface{

    private Activity context;

    // Pass the Activity in AndroidLauncher to this Class
    public AndroidInterfaceBluetoothClass(Activity context) {
        this.context = context;
    }

    // When the bluetooth button is disabled and user click on the button, the system will
    // check the bluetooth availability and connection with controllers, then perform accordingly
    @Override
    public void bluetoothUnavailableHandler() {

        context.runOnUiThread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void run() {
                BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
                int intVal = 2;
                if (BA == null) {
                    Toast.makeText(context.getApplicationContext(), "Bluetooth unavailable in this device", Toast.LENGTH_SHORT).show();
                } else {
                    if (BA.isEnabled()) {
                        if (getGameControllerIds().isEmpty()) {
                            Toast.makeText(context.getApplicationContext(), "No controller found", Toast.LENGTH_SHORT).show();
                        }
                        // This function will do nothing if a controller is connected
                        else {
                            return;
                        }
                    }
                    else {
                        // Request bluetooth permission if the permission is not granted
                        if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            context.requestPermissions(new String[] {Manifest.permission.BLUETOOTH_CONNECT},1);
                            return;
                        }
                        // Ask for turning on the bluetooth if the bluetooth permission is granted
                        // and the bluetooth is off
                        else {
                            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            context.startActivityForResult(i, intVal);
                        }
                    }
                }
            }
        });
    }

    // Check if a controller is successfully connected
    @Override
    public boolean isControllerAvailable() {
        if (!getGameControllerIds().isEmpty()) {
            return true;
        }
        return false;
    }

    // Show the message when a controller is connected and user press the bluetooth button
    @Override
    public void bluetoothAvailableMsg() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), "Controller connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show the message when bluetooth is on, no controller connected and user press the bluetooth button
    @Override
    public void bluetoothUnavailableMsg() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), "Controller disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Scan all connected devices, determines if they are supported game controllers
    // and finally return the number of them
    public ArrayList<Integer> getGameControllerIds() {
        ArrayList<Integer> gameControllerDeviceIds = new ArrayList<Integer>();
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                if (!gameControllerDeviceIds.contains(deviceId)) {
                    gameControllerDeviceIds.add(deviceId);
                }
            }
        }
        return gameControllerDeviceIds;
    }
}
