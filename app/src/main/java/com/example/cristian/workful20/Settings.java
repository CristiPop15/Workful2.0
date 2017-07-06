package com.example.cristian.workful20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.workful.Tools.AccountSingleton;
import com.workful.templates.CityList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Settings extends AppCompatActivity {

    ProgressDialog pg;
    private static int DELETE_ACCOUNT = 0;
    private static int DELETE_PROFILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pg = new ProgressDialog(this);

        Button d_a = (Button)findViewById(R.id.delete_account);
        Button d_p = (Button)findViewById(R.id.delete_profile);



        d_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteHttp(DELETE_ACCOUNT).execute();
            }
        });

        d_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteHttp(DELETE_PROFILE).execute();
            }
        });



    }


    private class DeleteHttp extends AsyncTask<Void,Void, Void>{
        int action;

        public DeleteHttp(int action) {
            this.action = action;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.setMessage("Loading..");
            pg.setCancelable(false);
            pg.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String url;

            if(action==DELETE_ACCOUNT) {
                url = Url.getUrl() + "delete-account?email=" + AccountSingleton.getCurrent().getEmail();
                AccountSingleton.getCurrent().accountLogout();
            }
            else
                url = Url.getUrl()+"delete-profile?id="+AccountSingleton.getCurrent().getId();
                AccountSingleton.getCurrent().accountLogout();


            Log.e("MainActivity", url);

            try {

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Boolean b = restTemplate.getForObject(url, Boolean.class);

                Log.e("MainActivity", b.toString());


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent i = new Intent(Settings.this,MainActivity.class);
            startActivity(i);
            Settings.this.finish();
        }
    }

}
