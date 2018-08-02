package com.mtw.diego.jokesonthego.helper.network;

abstract class BaseResponse {
    Integer status;
    String error;

    public String getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }
}