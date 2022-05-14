package com.example.game15;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        MusicAndRecord.getInstance(this);
        super.onCreate();
    }
}
