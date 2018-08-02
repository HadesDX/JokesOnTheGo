package com.mtw.diego.jokesonthego.helper.network;

import android.util.Log;

import com.mtw.diego.jokesonthego.entity.Joke;
import com.mtw.diego.jokesonthego.helper.Host;
import com.mtw.diego.jokesonthego.helper.exception.InvalidJokesHost;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class JokesService {
    private static final String TAG = "JokesJokesService";

    private static ApiService apiChuckNorris;
    private static ApiService apiICanHazDad;
    private static JokesService service;

    private JokesService() {
        if (apiChuckNorris == null) {
            try {
                apiChuckNorris = ApiClient.getClient(Host.CHUCK_NORRIS)
                        .create(ApiService.class);

            } catch (InvalidJokesHost invalidJokesHost) {
                invalidJokesHost.printStackTrace();
            }
        }
        if (apiICanHazDad == null) {
            try {
                apiICanHazDad = ApiClient.getClient(Host.I_CAN_HAZ_DAD_JOKE)
                        .create(ApiService.class);
            } catch (InvalidJokesHost invalidJokesHost) {
                invalidJokesHost.printStackTrace();
            }
        }
    }

    public static JokesService getJokesService() {
        if (service == null) {
            service = new JokesService();
        }
        return service;
    }

    public Observable<Joke> getJoke(Host host) {
        switch (host) {
            case CHUCK_NORRIS:
                return apiChuckNorris
                        .getChuckJoke()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(e ->
                                Converter.fromChuckNorris(e)
                        );
            case I_CAN_HAZ_DAD_JOKE:
                return apiICanHazDad
                        .getICanHazDad()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(e ->
                                Converter.fromICanHazDad(e)
                        );
        }
        return null;
    }
}
