package com.workful.templates;

/**
 * Created by Cristian on 4/30/2017.
 */
public class Common {
    protected int id;
    protected String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
