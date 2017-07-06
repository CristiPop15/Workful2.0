package com.workful.Tools.HttpCommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cristian.workful20.R;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.workful.templates.Category;
import com.workful.templates.CategoryList;
import com.workful.templates.City;
import com.workful.templates.CityList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Cristian on 5/1/2017.
 */

public class DropdownCategory extends AsyncTask<Void,Void,ArrayList<Category>> {

    private String url;
    private Spinner spinner;
    private CategoryList list;
    private Context context;
    private ProgressDialog pg;
    private ArrayAdapter adapter;

    private ArrayList<Category> categories;

    public DropdownCategory(Spinner spinner, String urlPrefix, Context c, ArrayList<Category> categories) {
        this.spinner = spinner;
        this.categories = categories;
        url = Url.getUrl() + urlPrefix;
        context = c;
        pg = new ProgressDialog(c);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg.setMessage("Loading..");
        pg.setCancelable(false);
        pg.show();
    }


    @Override
    protected ArrayList<Category> doInBackground(Void... params) {

        Log.e("MainActivity", url);

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            list = restTemplate.getForObject(url, CategoryList.class);

            Log.e("MainActivity", list.getList().toString());


            return list.getList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> listObject) {

        categories.addAll(listObject);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listObject);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();
    }
}
