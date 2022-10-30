package com.example.game;

import java.util.Map;

public interface FirebaseInterface {

    public void readData();

    public void updateData(int score, int alreadyUpdated);

    public Map<String, Object> transferData();
}
