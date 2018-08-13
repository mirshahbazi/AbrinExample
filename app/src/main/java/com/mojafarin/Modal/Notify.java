package com.mojafarin.Modal;
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi
 */
public class Notify {
    public String id = "";
    public String img = "";
    public String link = "";
    public String msg = "";
    public String title = "";
    public int view = 0;

    public Notify(String id, String title, String msg, String img, String link, int view) {
        this.id = id;
        this.title = title;
        this.msg = msg;
        this.img = img;
        this.link = link;
        this.view = view;
    }
}


