package com.example.cristian.workful20;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.workful.Tools.EmailVerification;
import com.workful.Tools.HttpCommunication.RegisterAccountHttp;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, confirm_password;
    private Button register_button;
    private TextView error_text;

    private String email_text, password_text, confirm_password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);

        register_button = (Button)findViewById(R.id.register_button);

        error_text = (TextView)findViewById(R.id.error_message);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });

    }

    private void checkInput(){

     //   try{
            email_text = String.valueOf(email.getText());
            password_text = String.valueOf(password.getText());
            confirm_password_text = String.valueOf(confirm_password.getText());


            if(checkEmail())
                if(checkPassword())
                    new RegisterAccountHttp(email_text, password_text, this, error_text).execute();
                else {
                    error_text.setText("Password mismatch");
                    error_text.setVisibility(View.VISIBLE);
                    Log.e("MainActivity", "password missmatch");


                }
            else{
                error_text.setText("Adresa de email nu este corect formata");
                error_text.setVisibility(View.VISIBLE);
                Log.e("MainActivity", "adresa de mail incorecta");
            }
    /*    }
        catch (NullPointerException e){
            e.printStackTrace();
            Log.e("MainActivity", "campuri goale");
            error_text.setText("Nu lasati campuri goale!");
            error_text.setVisibility(View.VISIBLE);
        }
        */

    }


    private boolean checkEmail(){
        return EmailVerification.validateEmail(email_text);
    }

    private boolean checkPassword(){
        return password_text.equals(confirm_password_text);
    }

}
