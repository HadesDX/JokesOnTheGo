package com.mtw.diego.jokesonthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mtw.diego.jokesonthego.helper.Utils;
import com.mtw.diego.jokesonthego.helper.network.JokesService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {
    private static String TAG = "JokesHome";
    private static final JokesService jokesService = JokesService.getJokesService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.applyPreferences(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_explore)
    public void launchExplore(View view) {
        Intent i = new Intent(this, Explore.class);
        startActivity(i);
    }

    @OnClick(R.id.buttton_favorites)
    public void launchFavorites(View view) {
        Intent i = new Intent(this, Favorites.class);
        startActivity(i);
    }

    @OnClick(R.id.button_settings)
    public void launchSettings(View view) {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}
