package com.example.game;

public interface BluetoothInterface {

    void bluetoothUnavailableHandler();

    boolean isControllerAvailable();

    void bluetoothAvailableMsg();

    void bluetoothUnavailableMsg();

}
