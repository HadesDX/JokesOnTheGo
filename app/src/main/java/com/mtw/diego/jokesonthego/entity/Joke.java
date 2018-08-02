package com.mtw.diego.jokesonthego.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.mtw.diego.jokesonthego.helper.Host;
import com.mtw.diego.jokesonthego.helper.database.HostConverter;

@Entity(tableName = "joke")
public class Joke {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "host")
    private Host host;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "joke")
    private String joke;

    @ColumnInfo(name = "icon")
    private String icon;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "favorite")
    private Boolean favorite;

    @ColumnInfo(name = "stars")
    private Integer stars;

    @ColumnInfo(name = "readtimes")
    private Integer readTimes;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(Integer readTimes) {
        this.readTimes = readTimes;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "uid=" + uid +
                ", host=" + host +
                ", id='" + id + '\'' +
                ", joke='" + joke + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", favorite=" + favorite +
                ", stars=" + stars +
                ", readTimes=" + readTimes +
                '}';
    }
}
