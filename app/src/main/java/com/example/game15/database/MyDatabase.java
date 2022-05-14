package com.example.game15.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.game15.model.Model;

@Database(entities = Model.class, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract ModelDao modelDao();

    private static MyDatabase instance = null;

    public static MyDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDatabase.class, "Model_db").allowMainThreadQueries().build();
        }
        return instance;
    }

}
