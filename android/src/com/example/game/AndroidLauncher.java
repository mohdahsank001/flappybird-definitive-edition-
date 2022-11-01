package com.example.game;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.content.Context;
import android.widget.Toast;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication {

	BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
	MainGame MG = new MainGame(new AndroidInterfaceBluetoothClass(this), new AndroidInterfaceClass(), new AndroidWeatherInterface());

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = true;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if(this.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
				this.requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO},1);
			}

		}

		checkBluetooth();
		initialize(MG, config);
	}

	@TargetApi(Build.VERSION_CODES.M)
	public void checkBluetooth() {
		if (BA == null) {
			MG.setBluetoothStatus(0); //Bluetooth Unavailable
		}
		else {
			if (BA.isEnabled()) {
				if (!getGameControllerIds().isEmpty()) {
					MG.setBluetoothStatus(1); //Bluetooth On Connected
				}
				else {
					MG.setBluetoothStatus(2); //Bluetooth On Unconnected
				}
			}
			else {
				MG.setBluetoothStatus(3); //Bluetooth Off
			}
		}
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
