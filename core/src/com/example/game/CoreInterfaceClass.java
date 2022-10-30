package com.example.game;

public class CoreInterfaceClass implements BluetoothInterface{
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
