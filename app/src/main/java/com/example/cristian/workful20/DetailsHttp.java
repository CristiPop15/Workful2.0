package com.example.cristian.workful20;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.workful.templates.Region;
import com.workful.templates.RegionList;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian on 4/27/2017.
 */

abstract class DetailsHttp extends AsyncTask<Void, Void, ArrayList> {


    protected String url = "http://192.168.2.31:8080/WebProjectWorkful/app/";

    protected ArrayAdapter adapter;
    protected Context context;

    protected Spinner spinner;
    protected ProgressDialog pg;


    public DetailsHttp(Spinner spinner, ProgressDialog pg, String urlPrefix, Context c) {
        this.spinner = spinner;
        this.pg = pg;
        url+=urlPrefix;
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg.setMessage("Loading..");
        pg.setCancelable(false);
        pg.show();
    }


}
