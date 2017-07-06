package com.workful.Tools.HttpCommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.workful20.ProfileView;
import com.workful.templates.Profile;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * Created by Cristian on 5/17/2017.
 */

public class HttpGetProfile extends AsyncTask<Void, Void, Profile> {

    private int id;
    private ProgressDialog pg;
    private Context mContext;


    public HttpGetProfile(int id, Context context) {
        this.id = id;
        pg = new ProgressDialog(context);
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg.setMessage("Loading...");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    protected Profile doInBackground(Void... params) {

        String url = Url.getUrl()+"get-profile?id="+id;

        Log.e("MainActivity", url);



            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Log.e("MainActivity", "After rest");

            Profile p = restTemplate.getForObject(url, Profile.class);
            Log.e("MainActivity", p.getSkills().toString());

            return p;



    }

    @Override
    protected void onPostExecute(Profile p) {

        if(p != null){
            Log.e("MainActivity", p.toString());

            Intent i = new Intent(mContext, ProfileView.class);
            i.putExtra("selectedProfile",  p);
            mContext.startActivity(i);
        }



        if(pg.isShowing())
            pg.dismiss();
    }
}
