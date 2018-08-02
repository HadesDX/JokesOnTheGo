package com.mtw.diego.jokesonthego.helper.network;

class ChuckNorrisResponse extends BaseResponse {

    private String id;
    private String value;
    private String url;
    private String icon_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    @Override
    public String toString() {
        return "ChuckNorrisResponse{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", url='" + url + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
