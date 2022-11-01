package com.example.game;

public class CoreInterfaceBluetoothClass implements BluetoothInterface{
    @Override
    public void bluetoothUnavailableHandler() {
    }

    @Override
    public boolean isControllerAvailable() {
        return false;
    }

    @Override
    public void bluetoothAvailableMsg() {
    }

    @Override
    public void bluetoothUnavailableMsg() {
    }
}
