package com.example.game15.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.game15.model.Model;

import java.util.List;

@Dao
public interface ModelDao {

    @Insert
    long insertResult(Model model);

    @Query("select * from Model where level=:level ")
    List<Model> getResultsByLevel(int level);

    @Delete
    void deleteResult(Model model);
}
