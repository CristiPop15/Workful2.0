package com.workful.templates;

/**
 * Created by Cristian on 7/4/2017.
 */
public class Comment {

    private int id_muncitor, rating;
    private String text, date, account_name, account_img;

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public int getId_muncitor() {
        return id_muncitor;
    }
    public void setId_muncitor(int id_muncitor) {
        this.id_muncitor = id_muncitor;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAccount_name() {
        return account_name;
    }
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
    public String getAccount_img() {
        return account_img;
    }
    public void setAccount_img(String account_img) {
        this.account_img = account_img;
    }


}
