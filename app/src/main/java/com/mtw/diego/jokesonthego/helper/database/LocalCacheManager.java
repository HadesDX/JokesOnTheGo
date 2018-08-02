package com.mtw.diego.jokesonthego.helper.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mtw.diego.jokesonthego.entity.Joke;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalCacheManager {
    private static final String DB_NAME = "database-name";
    private Context context;
    private static LocalCacheManager _instance;
    private AppDatabase db;

    public static LocalCacheManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new LocalCacheManager(context);
        }
        return _instance;
    }

    public LocalCacheManager(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public void getJokes(final DatabaseCallback databaseCallback) {
        db.jokeDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Joke>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull List<Joke> jokes) throws Exception {
                databaseCallback.onJokesLoaded(jokes);

            }
        });
    }

    public void addJoke(final DatabaseCallback databaseCallback, final String firstName, final String lastName) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Joke Joke = new Joke();
                db.jokeDao().insertAll(Joke);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                databaseCallback.onJokeAdded();
            }

            @Override
            public void onError(Throwable e) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }

    public void deleteJoke(final DatabaseCallback databaseCallback, final Joke joke) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.jokeDao().delete(joke);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                databaseCallback.onJokeDeleted();
            }

            @Override
            public void onError(Throwable e) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }


    public void updateJoke(final DatabaseCallback databaseCallback, final Joke Joke) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.jokeDao().updateJokes(Joke);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                databaseCallback.onJokeUpdated();
            }

            @Override
            public void onError(Throwable e) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }
}
