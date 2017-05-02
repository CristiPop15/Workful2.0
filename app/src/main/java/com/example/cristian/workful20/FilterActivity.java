package com.example.cristian.workful20;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.workful.Tools.DropdownCategory;
import com.workful.Tools.DropdownCity;
import com.workful.Tools.DropdownRegions;
import com.workful.templates.Category;
import com.workful.templates.CategoryList;
import com.workful.templates.Region;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private Spinner citiesSpinner, regionsSpinner, categorySpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regionsSpinner = (Spinner) findViewById(R.id.regionS);
        citiesSpinner = (Spinner)findViewById(R.id.cityS);
        categorySpinner = (Spinner)findViewById(R.id.categoryS);


        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Region r = (Region) regionsSpinner.getSelectedItem();

                String url = "/city?city="+r.getId();

                new DropdownCity(citiesSpinner, url, FilterActivity.this).execute();

                Log.e("MainActivity", r.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e("MainActivity", "filter activity -------------------------");

        new DropdownCategory(categorySpinner, "category", this).execute();
        new DropdownRegions(regionsSpinner, "region", this).execute();

    }






}



