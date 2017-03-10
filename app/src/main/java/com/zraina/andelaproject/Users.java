package com.zraina.andelaproject;

/**
 * Created by Okon on 2017-03-08.
 */

public class Users {
    private String login, img, url;

    public Users(String login, String img, String url) {
        this.login = login;
        this.img = img;
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
