package com.example.itcompanies;

import android.util.Log;

public class GlobalState {
    private static GlobalState instance;
    private int utilisateurId;

    private GlobalState() {
        utilisateurId = 1;
    }

    public static synchronized GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public int getUtilisateurId() {
        Log.d("GlobalState", "ID de l'utilisateur : " + utilisateurId);
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        Log.d("GlobalState", "Changement ID utilisateur : " + utilisateurId);
        this.utilisateurId = utilisateurId;
    }
}
