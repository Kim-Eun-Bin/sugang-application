package com.tpj.teamproject;

import java.io.Serializable;

public class Eclass implements Serializable {
    String title;
    String date;
    String info;
    String comment;
    String href;

    public Eclass(String title, String date, String info, String comment, String href){
        this.title = title;
        this.date = date;
        this.info = info;
        this.comment = comment;
        this.href = href;
    }
}