package com.haohui.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitosDao {
    @Insert
    void insert(Habitos habitos);

    @Delete
    void delete(Habitos habitos);

    @Update
    void update(Habitos habitos);


    @Query("SELECT * FROM Habitos")
    List<Habitos> getAllHabitos();
}
