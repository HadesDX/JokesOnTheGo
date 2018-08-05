package com.mtw.diego.jokesonthego;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.mtw.diego.jokesonthego.favorites.FavoritesAdapter;
import com.mtw.diego.jokesonthego.favorites.SwipeDelegate;
import com.mtw.diego.jokesonthego.helper.database.AppDatabase;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Favorites extends Activity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeDelegate swipeDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.activity_favorites);
        mLayoutManager = new LinearLayoutManager(this);
        RecyclerView favorites = findViewById(R.id.recicler_favorites);
        favorites.setLayoutManager(mLayoutManager);
        swipeDelegate = new SwipeDelegate();
        Disposable d = AppDatabase.getDatabase(this).jokeDao().getFavorites().
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(e -> {
                    mAdapter = new FavoritesAdapter(e, this);
                    swipeDelegate.setAdapter((FavoritesAdapter) mAdapter);
                    favorites.setAdapter(mAdapter);
                });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeDelegate);
        itemTouchhelper.attachToRecyclerView(favorites);

    }

}
