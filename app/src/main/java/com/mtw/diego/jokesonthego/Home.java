package com.mtw.diego.jokesonthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mtw.diego.jokesonthego.helper.database.AppDatabase;
import com.mtw.diego.jokesonthego.helper.network.JokesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Home extends AppCompatActivity {
    private static String TAG = "JokesHome";
    private static final JokesService jokesService = JokesService.getJokesService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Disposable d = AppDatabase.getDatabase(this).jokeDao().countAvaibleJokes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> {
                    if (e != null) {
                        Log.i(TAG, "Total not so old jokes: " + e.toString());
                    }
                });
        Disposable d2 = Observable.fromCallable(() -> {
            AppDatabase.getDatabase(this).jokeDao().cleanUpOldJokes();
            return true;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();


    }

    public void launchExplore(View view) {
        Intent i = new Intent(this, Explore.class);
        startActivity(i);
    }

    public void launchSettings(View view) {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}
