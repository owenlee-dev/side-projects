package com.example.soundhoard;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Soundboard.class, Sound.class, SoundboardHasSound.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    public abstract SoundboardDao soundboardDao();
    public abstract SoundboardHasSoundDao soundboardHasSoundDao();
    public abstract SoundDao soundDao();

    static AppDatabase getInstance(Context context) {
        if(database == null) {
            synchronized(AppDatabase.class) {
                if(database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "SoundHoard").allowMainThreadQueries().build();
                }
            }
        }
        return database;
    }
}