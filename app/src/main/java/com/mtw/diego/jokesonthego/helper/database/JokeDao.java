package com.mtw.diego.jokesonthego.helper.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mtw.diego.jokesonthego.entity.Joke;
import com.mtw.diego.jokesonthego.helper.Host;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface JokeDao {
    @Query("SELECT * FROM joke")
    public Maybe<List<Joke>> getAll();

    @Query("SELECT * FROM joke where favorite=1")
    public Maybe<List<Joke>> getFavorites();

    @Query("SELECT * FROM joke WHERE favorite = 0 and readtimes > :readTimes ")
    public Maybe<List<Joke>> getBurnedJokes(int readTimes);

    @Query("SELECT * FROM joke WHERE uid IN (:jokeIds)")
    public Maybe<List<Joke>> loadAllByIds(int[] jokeIds);

    @Query("SELECT * FROM joke WHERE host = :host")
    public Maybe<List<Joke>> findByHost(Host host);

    @Query("SELECT * FROM joke where uid = :id")
    public Maybe<Joke> findById(int id);

    @Query("SELECT * FROM joke where favorite=0 order by readtimes limit 1")
    public Maybe<Joke> findLessReadedJoke();

    @Query("SELECT count(1) FROM joke where favorite=0 and readtimes <:readTimes")//3
    public Maybe<Integer> countAvaibleJokes(int readTimes);

    @Query("DELETE FROM joke where favorite=0 and readtimes>:readTimes")//3
    public void cleanUpOldJokes(int readTimes);

    @Insert
    public void insertAll(Joke... jokes);

    @Delete
    public void delete(Joke joke);

    @Update
    public void updateJokes(Joke... jokes);


}
