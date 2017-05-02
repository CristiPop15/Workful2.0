package com.workful.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cristian.workful20.R;
import com.workful.templates.City;
import com.workful.templates.CityList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Cristian on 5/1/2017.
 */

public class DropdownCity extends AsyncTask<Void, Void, ArrayList<City>> {

    private String url;
    private Spinner spinner;
    private CityList list;
    private Context context;
    private ProgressDialog pg;
    private ArrayAdapter adapter;




    public DropdownCity(Spinner spinner, String urlPrefix, Context c) {
        this.spinner = spinner;
        url = Url.getUrl() + urlPrefix;
        pg = new ProgressDialog(c);
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
    protected void onPostExecute(ArrayList<City> listObject) {



        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listObject);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();


    }


    @Override
    protected ArrayList<City> doInBackground(Void... params) {

        Log.e("MainActivity", url);

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            list = restTemplate.getForObject(url, CityList.class);

            Log.e("MainActivity", list.getList().toString());


            return list.getList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new CityList().getList();
    }
}
