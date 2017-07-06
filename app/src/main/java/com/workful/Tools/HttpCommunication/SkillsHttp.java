package com.workful.Tools.HttpCommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.workful.templates.Category;
import com.workful.templates.Skill;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Cristian on 6/30/2017.
 */

public class SkillsHttp extends AsyncTask<Void, Void, ArrayList<Skill>> {

    private String url;
    private ProgressDialog pg;

    private ArrayList<Skill> skills;

    public SkillsHttp(Context context, Category category) {
        pg  = new ProgressDialog(context);
        url = Url.getUrl() + "get-skills?category="+category.getId();
    }

    @Override
    protected void onPostExecute(ArrayList<Skill> aVoid) {
        super.onPostExecute(aVoid);

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pg.setMessage("Loading..");
        pg.setCancelable(false);
        pg.show();

    }

    @Override
    protected ArrayList<Skill> doInBackground(Void... params) {

        Log.e("MainActivity", url);

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            skills = restTemplate.getForObject(url, ArrayList.class);

            Log.e("MainActivity", skills.toString());


            return skills;

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
