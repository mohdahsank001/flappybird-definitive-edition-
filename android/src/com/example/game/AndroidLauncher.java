package com.example.game;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.view.InputDevice;
import android.view.KeyEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication {

	BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
	MainGame MG = new MainGame(new AndroidInterfaceBluetoothClass(this),
			new AndroidInterfaceClass(), new AndroidWeatherInterface(),
			new AndroidLatLonInterfaceClass(this));


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = true;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if(this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
			 &&
					this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				this.requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO},1);
			}

		}

		checkBluetooth();
		initialize(MG, config);
	}

	// Check current bluetooth status and set the blueToothStatus in MainGame
	@TargetApi(Build.VERSION_CODES.M)
	public void checkBluetooth() {
		if (BA == null) {
			//Bluetooth Unavailable
			MG.setBluetoothStatus(0);
		}
		else {
			if (BA.isEnabled()) {
				if (!getGameControllerIds().isEmpty()) {
					//Bluetooth On and Connected
					MG.setBluetoothStatus(1);
				}
				else {
					//Bluetooth On and Unconnected
					MG.setBluetoothStatus(2);
				}
			}
			else {
				//Bluetooth Off
				MG.setBluetoothStatus(3);
			}
		}
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

	// Detect any input of controllers, when the input equals Button A or DPad Center
	// it will return true and call the function bluetoothPress() in MainGame
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = false;
		if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
				== InputDevice.SOURCE_GAMEPAD) {
			if (event.getRepeatCount() == 0) {
				switch (keyCode) {
					default:
						if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
								|| keyCode == KeyEvent.KEYCODE_BUTTON_A) {
							MG.bluetoothPress();
							handled = true;
						}
						break;
				}
			}
			if (handled) {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
