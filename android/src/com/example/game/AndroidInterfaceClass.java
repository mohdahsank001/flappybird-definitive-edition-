package com.example.game;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.view.InputDevice;
import android.widget.Toast;

import java.util.ArrayList;

public class AndroidInterfaceClass implements BluetoothInterface {
    private Activity context;

    public AndroidInterfaceClass(Activity context) {
        this.context = context;
    }

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
                        else {
                            return;
                        }
                    }
                    else {
                        //Toast.makeText(context.getApplicationContext(), "Please turn on the Bluetooth", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        context.startActivityForResult(i, intVal);
                        if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            context.requestPermissions(new String[] {Manifest.permission.BLUETOOTH_CONNECT},1);
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean isControllerAvailable() {
        if (!getGameControllerIds().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void bluetoothAvailableMsg() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), "Controller connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void bluetoothUnavailableMsg() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), "Controller disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
