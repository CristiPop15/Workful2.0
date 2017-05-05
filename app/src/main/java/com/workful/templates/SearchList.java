package com.workful.templates;

import java.util.ArrayList;

/**
 * Created by Cristian on 5/4/2017.
 */
public class SearchList {
    private ArrayList<SearchResult> list;
    private int rows;
    public ArrayList<SearchResult> getList() {
        return list;
    }
    public void setList(ArrayList<SearchResult> list) {
        this.list = list;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {}
}
