package com.haohui.myapplication;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient instance;
    private HabitoDatabase habitoDatabase;

    private DatabaseClient(Context context) {
        this.context = context;
        habitoDatabase = Room.databaseBuilder(context, HabitoDatabase.class, "HabitoDB")
                .allowMainThreadQueries()
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public HabitoDatabase getHabitoDatabase() {
        return habitoDatabase;
    }
}
