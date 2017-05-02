package com.example.cristian.workful20;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.workful.Tools.AccountSingleton;
import com.workful.Tools.ProfileInformationHttp;
import com.workful.templates.AccountInfo;
import com.workful.templates.CategoryList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login_button = (Button)findViewById(R.id.loginButton);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    Log.e("MainActivity", "Login button clicked");

                    String emailS = email.getText().toString();
                    String passwordS = password.getText().toString();

                    String url_prefix = "login?email="+emailS+"&password="+passwordS;

                    if(AccountSingleton.getCurrent() == null)
                        new LoginHttp(url_prefix, Login.this).execute();
                    else
                        Log.e("MainActivity", "Already logged in");

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

    }

    private void goBackToMain(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }

    class LoginHttp extends AsyncTask<Void, Void, AccountInfo>{

        private String url;
        private AccountInfo accountInfo;
        private ProgressDialog pg;
        private Context c;


        public LoginHttp(String url, Context c) {
            this.url = Url.getUrl() + url;
            pg = new ProgressDialog(c);
            this.c = c;
            accountInfo = new AccountInfo();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setMessage("Logging in..");
            pg.setCancelable(false);
            pg.show();
        }

        @Override
        protected AccountInfo doInBackground(Void... params) {

            Log.e("MainActivity", url);

            try {

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                accountInfo = restTemplate.getForObject(url, AccountInfo.class);

                Log.e("MainActivity", accountInfo.toString());

                return accountInfo;


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(AccountInfo accountInfo) {

            if(AccountSingleton.getCurrent() == null)
                if(accountInfo == null)
                    Log.e("MainActivity", "Server object null. Bad credentials");
                else {
                    AccountSingleton.createAccount(accountInfo.getEmail(), accountInfo.getId());
                    if(accountInfo.isRegistered_as_worker()){
                        new ProfileInformationHttp(c, accountInfo.getId()).execute();
                    }

                    goBackToMain();

                }
            else
                Log.e("MainActivity", "Already logged in");


            //end progress dialog
            if(pg.isShowing())
                pg.dismiss();

        }
    }
}

