package com.mtw.diego.jokesonthego.helper.network;

class ICanHazDadResponse extends BaseResponse {

    private String id;
    private String joke;


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

    @Override
    public String toString() {
        return "ICanHazDadResponse{" +
                "id='" + id + '\'' +
                ", joke='" + joke + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
