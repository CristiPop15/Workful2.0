package com.workful.templates;

/**
 * Created by Cristian on 5/2/2017.
 */
public class AccountInfo {
    private String email;
    private int id;
    private String full_name;

    public String getFull_name() {
        return full_name;
    }


    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
