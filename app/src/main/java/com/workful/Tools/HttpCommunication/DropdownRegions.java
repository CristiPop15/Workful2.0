package com.workful.Tools.HttpCommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.cristian.workful20.R;
import com.workful.templates.City;
import com.workful.templates.Region;
import com.workful.templates.RegionList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Cristian on 5/1/2017.
 */
public class DropdownRegions extends AsyncTask<Void, Void, ArrayList<Region>> {

    private String url;

    private ArrayAdapter adapter;
    private Context context;

    private RegionList list;


    private Spinner spinner;
    private ProgressDialog pg;

    public DropdownRegions(Spinner spinner, String urlPrefix, Context c) {
        this.spinner = spinner;
        pg = new ProgressDialog(c);
        url = Url.getUrl() + urlPrefix;
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg.setMessage("Loading..");
        pg.setCancelable(false);
        pg.show();
    }





    @Override
    protected ArrayList<Region> doInBackground(Void... params) {

        Log.e("MainActivity", url);


        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            list = restTemplate.getForObject(url, RegionList.class);

            Log.e("MainActivity", list.getList().toString());

            return list.getList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPostExecute(ArrayList<Region> listObject) {

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listObject);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();


    }
}
