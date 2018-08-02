package com.mtw.diego.jokesonthego.helper.network;

import com.mtw.diego.jokesonthego.entity.Joke;
import com.mtw.diego.jokesonthego.helper.Host;

class Converter {
    public static Joke fromChuckNorris(ChuckNorrisResponse r) {
        Joke o = new Joke();
        o.setFavorite(false);
        o.setHost(Host.CHUCK_NORRIS);
        o.setIcon(r.getIcon_url());
        o.setId(r.getId());
        o.setJoke(r.getValue());
        o.setStars(0);
        o.setUrl(r.getUrl());
        o.setReadTimes(0);
        return o;
    }

    public static Joke fromICanHazDad(ICanHazDadResponse r) {
        Joke o = new Joke();
        o.setFavorite(false);
        o.setHost(Host.I_CAN_HAZ_DAD_JOKE);
        o.setId(r.getId());
        o.setJoke(r.getJoke());
        o.setStars(0);
        o.setReadTimes(0);
        return o;
    }
}
