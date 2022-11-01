package com.example.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AndroidInterfaceClass implements FirebaseInterface{
    FirebaseFirestore db;
    CollectionReference docRef;
    public Map<String, Object> current_leaderboard = new HashMap<>();
    private static final String TAG = "AndroidInterfaceClass";

    public AndroidInterfaceClass(){
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("Leaderboard");
    }

    @Override
    public void readData() {
        docRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                current_leaderboard.putAll(document.getData());
                            }
                        }
                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void updateData(int score, int alreadyUpdated) {
        if (alreadyUpdated == 1){
            return;
        }
        docRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                current_leaderboard.putAll(document.getData());
                                for (int i = 0; i <5; i++){
                                    String temp = "";
                                    if (Integer.parseInt(String.valueOf(current_leaderboard.get(Integer.toString(i+1)))) < score){

                                        temp = String.valueOf(current_leaderboard.get(Integer.toString(i+1)));
                                        docRef.document("Rank").update(Integer.toString(i+1), score);

                                        for (int j = i+1; j < 5; j++) {
                                            String temp1 = String.valueOf(current_leaderboard.get(Integer.toString(j+1)));
                                            docRef.document("Rank").update(Integer.toString(j+1), temp);
                                            temp = temp1;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            Log.w(TAG, "Error updating documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public Map<String, Object> transferData() {
        return current_leaderboard;
    }
}
