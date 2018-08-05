package com.mtw.diego.jokesonthego.helper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.mtw.diego.jokesonthego.entity.Joke;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {Joke.class}, version = 1, exportSchema = false)
@TypeConverters(HostConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static int MAX_NEW_JOKES = 60;
    public static int JOKES_OLD = 3;

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

    public static synchronized void doClean(Context c) {
        Disposable d2 = Observable.fromCallable(() -> {
            AppDatabase.getDatabase(c).jokeDao().cleanUpOldJokes(JOKES_OLD);
            return true;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    public static synchronized void d() {
    }

}