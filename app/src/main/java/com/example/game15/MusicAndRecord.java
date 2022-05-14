package com.example.game15;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

public class MusicAndRecord {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void getInstance(Context context) {
        preferences = context.getSharedPreferences("MUSIC", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public static void saveMusicState(Boolean state) {
        editor.putBoolean("musicState", state);
        editor.apply();
    }

    public static void saveVolumeState(Boolean state) {
        editor.putBoolean("volumeState", state);
        editor.apply();
    }

    public static boolean getMusicState() {
        return preferences.getBoolean("musicState", false);
    }

    public static boolean getVolumeState() {
        return preferences.getBoolean("volumeState", false);
    }

    public static void saveResults(int count, int total, String time) {
        int firstCount = getFirstCount();
        int firstTotal = getFirstTotal();
        String firstTime = getFirstTime();
        int secondCount = getSecondCount();
        int secondTotal = getSecondTotal();
        String secondTime = getSecondTime();

        if (total < getFirstTotal()) {
            setFirstTimeCount(count, total, time);
            setSecondTimeCount(firstCount, firstTotal, firstTime);
            setThirdTimeCount(secondCount, secondTotal, secondTime);

        } else if (total < getSecondTotal()) {
            setSecondTimeCount(count, total, time);
            setThirdTimeCount(secondCount, secondTotal, secondTime);

        } else if (total < getThirdTotal()) {
            setThirdTimeCount(count, total, time);
        }


    }

    public static void clear() {
        preferences.edit().clear().apply();
    }


    public static void setFirstTimeCount(int count, int total, String time) {
        editor.putInt("firstCount", count);
        editor.putInt("firstTotal", total);
        editor.putString("firstTime", time);
        editor.apply();
    }

    public static String getFirstTime() {
        return preferences.getString("firstTime", "");
    }

    public static int getFirstCount() {
        return preferences.getInt("firstCount", 0);
    }

    public static int getFirstTotal() {
        return preferences.getInt("firstTotal", 1000000);
    }


    public static void setSecondTimeCount(int count, int total, String time) {
        editor.putInt("secondCount", count);
        editor.putInt("secondTotal", total);
        editor.putString("secondTime", time);
        editor.apply();
    }

    public static String getSecondTime() {
        return preferences.getString("secondTime", "");
    }

    public static int getSecondCount() {
        return preferences.getInt("secondCount", 0);
    }

    public static int getSecondTotal() {
        return preferences.getInt("secondTotal", 1000000);
    }


    public static void setThirdTimeCount(int count, int total, String time) {
        editor.putInt("thirdCount", count);
        editor.putInt("thirdTotal", total);
        editor.putString("thirdTime", time);
        editor.apply();
    }

    public static String getThirdTime() {
        return preferences.getString("thirdTime", "");
    }

    public static int getThirdCount() {
        return preferences.getInt("thirdCount", 0);
    }

    public static int getThirdTotal() {
        return preferences.getInt("thirdTotal", 1000000);
    }

}

