package com.example.game;

import java.util.Map;

// The Interface for calling methods from the Android module
// due to the imcompatibility of gdxlib and other libs.
public interface FirebaseInterface {

    void readData();

    void updateData(int score, int alreadyUpdated);

    Map<String, Object> transferData();
}
