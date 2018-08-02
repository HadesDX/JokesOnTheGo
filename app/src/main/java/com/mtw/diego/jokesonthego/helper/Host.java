package com.mtw.diego.jokesonthego.helper;

public enum Host {
    CHUCK_NORRIS(0, "Chuck Norris", "https://api.chucknorris.io/"),
    I_CAN_HAZ_DAD_JOKE(1, "I Can Haz Dad", "https://icanhazdadjoke.com/");

    private final String url;
    private final int index;
    private final String name;

    Host(int index, String name, String url) {
        this.url = url;
        this.index = index;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
