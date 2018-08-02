package com.mtw.diego.jokesonthego.helper.network;

import com.mtw.diego.jokesonthego.helper.Host;
import com.mtw.diego.jokesonthego.helper.exception.InvalidJokesHost;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {
    private static Retrofit retrofitChuckNorris = null;
    private static Retrofit retrofitICanHazDad = null;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;


    public static Retrofit getClient(Host host) throws InvalidJokesHost {

        if (okHttpClient == null) {
            initOkHttp();
        }

        if (retrofitChuckNorris == null) {
            retrofitChuckNorris = new Retrofit.Builder()
                    .baseUrl(Host.CHUCK_NORRIS.getUrl())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        if (retrofitICanHazDad == null) {
            retrofitICanHazDad = new Retrofit.Builder()
                    .baseUrl(Host.I_CAN_HAZ_DAD_JOKE.getUrl())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        switch (host) {
            case CHUCK_NORRIS:
                return retrofitChuckNorris;
            case I_CAN_HAZ_DAD_JOKE:
                return retrofitICanHazDad;
            default:
                throw new InvalidJokesHost();
        }
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
    }
}