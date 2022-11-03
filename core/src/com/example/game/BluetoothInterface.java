package com.example.game;

// The Interface for calling methods from the Android module
// due to the imcompatibility of gdxlib and other libs.
public interface BluetoothInterface {

    void bluetoothUnavailableHandler();

    boolean isControllerAvailable();

    void bluetoothAvailableMsg();

    void bluetoothUnavailableMsg();

}
