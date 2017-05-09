package com.workful.Tools.HttpCommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.workful.Tools.AccountSingleton;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Cristian on 5/2/2017.
 */

public class ProfileInformationHttp extends AsyncTask<Void, Void, Void> {

    private ProgressDialog pg;
    private int account_id;


    public ProfileInformationHttp(Context c, int account_id) {
        pg = new ProgressDialog(c);
        this.account_id = account_id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg.setMessage("Setting up your profile..");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.e("MainActivity", "getting profile information");

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String url = Url.getUrl()+"get-profile?account_id="+account_id;

            Log.e("MainActivity", url);

            String full_name = restTemplate.getForObject(url, String.class);

            AccountSingleton.getCurrent().setFull_name(full_name);

            return null;


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();
    }
}

