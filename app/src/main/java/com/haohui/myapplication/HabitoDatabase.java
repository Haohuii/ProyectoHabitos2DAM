package com.haohui.myapplication;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Habitos.class}, version = 1)
public abstract class HabitoDatabase extends RoomDatabase {
    public abstract HabitosDao habitosDao();
}
