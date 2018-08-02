package com.mtw.diego.jokesonthego.helper.database;

import com.mtw.diego.jokesonthego.entity.Joke;

import java.util.List;

public interface DatabaseCallback {

    void onJokesLoaded(List<Joke> users);

    void onJokeDeleted();

    void onJokeAdded();

    void onDataNotAvailable();

    void onJokeUpdated();
}