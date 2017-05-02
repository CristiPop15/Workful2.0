package com.workful.templates;

/**
 * Created by Cristian on 5/2/2017.
 */
public class AccountInfo {
    private String email;
    private int id;
    private boolean registered_as_worker;

    public boolean isRegistered_as_worker() {
        return registered_as_worker;
    }

    public void setRegistered_as_worker(boolean registered_as_worker) {
        this.registered_as_worker = registered_as_worker;
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
