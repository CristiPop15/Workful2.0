package com.workful.Tools.HttpCommunication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.workful.templates.CityList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Cristian on 7/4/2017.
 */
public class AddCommentHttp extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private String text, rating;
    private ProgressDialog pg;
    private int id, profile_id;
    private Activity a;

    public AddCommentHttp(Context mContext, String text, String rating, int id, int profile_id, Activity a) {
        this.mContext = mContext;
        this.text = text;
        this.rating = rating;
        this.id = id;
        this.profile_id = profile_id;
        pg = new ProgressDialog(mContext);
        this.a = a;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pg.setMessage("Saving..");
        pg.setCancelable(true);
        pg.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        String url = Url.getUrl() + "add-comment?id="+id+"&profile="+profile_id+"&nota="+rating+"&text="+text;

        Log.e("MainActivity", url);

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            Integer response = restTemplate.getForObject(url, Integer.class);



        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //end progress dialog
        if(pg.isShowing())
            pg.dismiss();

        new HttpGetProfile(profile_id, mContext).execute();
        a.finish();
    }
}
