package com.workful.Tools.HttpCommunication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cristian.workful20.MainActivity;
import com.workful.Tools.SearchAdapter;
import com.workful.templates.Account;
import com.workful.templates.CategoryList;
import com.workful.templates.SearchList;
import com.workful.templates.SearchResult;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Cristian on 5/4/2017.
 */

public class SearchHttpRequest extends AsyncTask<Void, Void, ArrayList<SearchResult>> {

    private String query = null;
    private SearchList searchObj;
    SearchAdapter adapter;
    ArrayList<SearchResult> lst;
    private ProgressDialog pg;
    private int page;
    private boolean ok;


    private String url_search;

    public SearchHttpRequest(String query, Context c, SearchAdapter adapter, int page, ArrayList<SearchResult> lst, boolean ok) {
        this.lst = lst;
        this.query = query;
        this.page = page;
        this.adapter = adapter;

        pg = new ProgressDialog(c);

        this.ok = ok;


    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pg.setMessage("Searching..");
        pg.setCancelable(true);
        pg.setCanceledOnTouchOutside(true);
        pg.show();

        url_search = Url.getUrl() + query + String.format("&limit=%s",page);
    }

    @Override
    protected ArrayList<SearchResult> doInBackground(Void... params) {

        Log.e("MainActivity", url_search);

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Log.e("MainActivity", "After rest template");


            searchObj = restTemplate.getForObject(url_search, SearchList.class);

            Log.e("MainActivity", searchObj.getList().toString());


            return searchObj.getList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<SearchResult> result) {

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();
        Log.e("MainActivity", "postexecute after pg");



        if(result != null){
            if(lst.size()>=1) {
                int last = lst.size() - 1;
                lst.addAll(result);
                adapter.notifyItemRangeInserted(last, 10);
            }
            else {
                int last = lst.size();
                lst.addAll(result);
                adapter.notifyItemRangeInserted(last, 10);
            }
        }



    }
}
