package com.mtw.diego.jokesonthego.helper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.mtw.diego.jokesonthego.entity.Joke;

@Database(entities = {Joke.class}, version = 1, exportSchema = false)
@TypeConverters(HostConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static int MAX_NEW_JOKES = 60;

    public abstract JokeDao jokeDao();

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "jokes_database")
                            .build();
                }
            }
        }
        return instance;
    }

}