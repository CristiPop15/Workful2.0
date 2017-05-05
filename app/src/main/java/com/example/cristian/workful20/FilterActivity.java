package com.example.cristian.workful20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.workful.Tools.DropdownCategory;
import com.workful.Tools.DropdownCity;
import com.workful.Tools.DropdownRegions;
import com.workful.templates.Category;
import com.workful.templates.CategoryList;
import com.workful.templates.City;
import com.workful.templates.Region;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private Spinner citiesSpinner, regionsSpinner, categorySpinner;
    private Button search;
    private EditText query;

    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regionsSpinner = (Spinner) findViewById(R.id.regionS);
        citiesSpinner = (Spinner)findViewById(R.id.cityS);
        categorySpinner = (Spinner)findViewById(R.id.categoryS);
        search = (Button)findViewById(R.id.search_button);
        query = (EditText)findViewById(R.id.query);

        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Region r = (Region) regionsSpinner.getSelectedItem();

                String url = "/city?city="+r.getId();

                new DropdownCity(citiesSpinner, url, FilterActivity.this, cities).execute();

                Log.e("MainActivity", r.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e("MainActivity", "filter activity -------------------------");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSearchIntent();
            }
        });

        new DropdownCategory(categorySpinner, "category", this, categories).execute();
        new DropdownRegions(regionsSpinner, "region", this).execute();

    }


    private void sendSearchIntent(){
        City city = cities.get((int) citiesSpinner.getSelectedItemId());
        Category category = categories.get((int)categorySpinner.getSelectedItemId());
        String search_query;

        try {
            search_query = String.valueOf(query.getText());
        }
        catch (NullPointerException e){
            search_query = "";
            e.printStackTrace();

        }

        Intent i = new Intent(this, MainActivity.class);

        i.putExtra("query", search_query);
        i.putExtra("oras_id", city.getId());
        i.putExtra("categorie_id", category.getId());

        startActivity(i);

    }






}



