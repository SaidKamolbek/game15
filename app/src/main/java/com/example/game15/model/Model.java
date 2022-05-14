package com.example.game15.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Model {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String time;
    public long result;
    public int count;
    public int level;

    public Model(int id, String name, String time, long result, int count, int level) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.result = result;
        this.count = count;
        this.level = level;
    }

}
