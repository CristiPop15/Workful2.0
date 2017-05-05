package com.workful.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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


    private String url_search;

    public SearchHttpRequest(String query, Context c, SearchAdapter adapter, int page, ArrayList<SearchResult> lst) {
        this.lst = lst;
        this.query = query;
        this.page = page;
        this.adapter = adapter;

        pg = new ProgressDialog(c);
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pg.setMessage("Searching..");
        pg.setCancelable(false);
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


        if(result != null){
            int last = lst.size()-1;
            lst.addAll(result);
            adapter.notifyItemRangeInserted(last, 10);
        }

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();
    }
}
