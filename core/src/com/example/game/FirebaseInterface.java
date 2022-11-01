package com.example.game;

import java.util.Map;

public interface FirebaseInterface {

    void readData();

    void updateData(int score, int alreadyUpdated);

    Map<String, Object> transferData();
}
