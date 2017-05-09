package com.workful.Tools.HttpCommunication;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import com.workful.templates.Account;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Cristian on 5/5/2017.
 */
public class RegisterAccountHttp extends AsyncTask<Void, Void, Integer> {

    private TextView error_text;
    private ProgressDialog pg;
    private Account account;


    public RegisterAccountHttp(String email, String password, Context c, TextView error_text) {
        account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        this.error_text = error_text;
        pg = new ProgressDialog(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg.setMessage("Registering..");
        pg.setCancelable(true);
        pg.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        String url = Url.getUrl() + "register-new-account";

        Log.e("MainActivity", url);

        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Log.e("MainActivity", "After rest template");

            Integer response = restTemplate.postForObject(url, account, Integer.class);

            Log.e("MainActivity", response.toString());

            return response;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer success) {

        //end progress dialog
        if (pg.isShowing())
            pg.dismiss();

        if(success != null) {

            if (success == 2)
                error_text.setText("Emailul este deja luat!");
            else if (success == 0)
                error_text.setText("A aparut o eroare, incercati din nou");
            else if (success == 1)
                error_text.setText("Succes!");
        }
    }


}