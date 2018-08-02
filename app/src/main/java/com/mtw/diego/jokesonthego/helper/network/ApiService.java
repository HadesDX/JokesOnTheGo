package com.mtw.diego.jokesonthego.helper.network;

import io.reactivex.Observable;
import retrofit2.http.GET;

interface ApiService {

    @GET("jokes/random")
    public Observable<ChuckNorrisResponse> getChuckJoke();

    @GET("")
    public Observable<ICanHazDadResponse> getICanHazDad();

}
