package com.mtw.diego.jokesonthego.helper.network;

import android.content.Context;
import android.util.Log;

import com.mtw.diego.jokesonthego.entity.Joke;
import com.mtw.diego.jokesonthego.helper.Host;
import com.mtw.diego.jokesonthego.helper.database.AppDatabase;
import com.mtw.diego.jokesonthego.helper.exception.InvalidJokesHost;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class JokesService {
    private static final String TAG = "JokesJokesService";

    private static ApiService apiChuckNorris;
    private static ApiService apiICanHazDad;
    private static JokesService service;

    public static ArrayList<Integer> HOSTS;
    public static int currentHost = 0;

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

    public static void downloadMoreJokes(Context c) {

    }

    public Observable<Joke> getJoke() {
        if (HOSTS == null) {
            HOSTS = new ArrayList<>();
        }
        if (HOSTS.isEmpty()) {
            HOSTS.add(0);
        }
        if (currentHost >= HOSTS.size()) {
            currentHost = 0;
        }
        Host host = Host.getHost(HOSTS.get(currentHost));
        Log.d(TAG, currentHost + "");
        Log.d(TAG, HOSTS.get(currentHost) + "");
        Log.d(TAG, host.getName());
        currentHost++;
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
