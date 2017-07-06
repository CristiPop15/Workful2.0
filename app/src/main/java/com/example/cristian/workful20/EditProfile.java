package com.example.cristian.workful20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.workful.Tools.AccountSingleton;
import com.workful.Tools.HttpCommunication.DropdownCategory;
import com.workful.Tools.HttpCommunication.DropdownCity;
import com.workful.Tools.HttpCommunication.DropdownRegions;
import com.workful.Tools.HttpCommunication.SkillsHttp;
import com.workful.Tools.ImgHandler;
import com.workful.templates.AppProfile;
import com.workful.templates.Category;
import com.workful.templates.City;
import com.workful.templates.Profile;
import com.workful.templates.Region;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;


    private Spinner regionsSpinner, categorySpinner, citiesSpinner;
    private ImageView img;
    private Button button;
    private TextView name, surname, description, phone, title;

    private ProgressDialog pg;

    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    private ArrayList<TextView> text_views = new ArrayList<>();

    String picturePath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        img = (ImageView)findViewById(R.id.prfimg);
        regionsSpinner = (Spinner) findViewById(R.id.regionS);
        citiesSpinner = (Spinner)findViewById(R.id.cityS);
        categorySpinner = (Spinner)findViewById(R.id.categoryS);
        name = (TextView)findViewById(R.id.name_text);
        surname = (TextView)findViewById(R.id.surname_text);
        description = (TextView)findViewById(R.id.description_text);
        phone = (TextView)findViewById(R.id.phone_text);
        title = (TextView)findViewById(R.id.title_text);
        button = (Button)findViewById(R.id.button);

        text_views.add(name);
        text_views.add(surname);
        text_views.add(description);
        text_views.add(phone);
        text_views.add(title);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  new SkillsHttp(EditProfile.this, (Category)categorySpinner.getSelectedItem()).execute();



                if(validation())
                    if(phone.getText().length()==10) {
                        pg = new ProgressDialog(EditProfile.this);

                        pg.setMessage("Loading..");
                        pg.setCancelable(false);
                        pg.show();
                        new ProfileEditHttp().execute();
                    }
                else {

                    Toast.makeText(EditProfile.this, "Completati toate campurile!!", Toast.LENGTH_LONG).show();
                }
                else {

                    Toast.makeText(EditProfile.this, "Completati toate campurile!!", Toast.LENGTH_LONG).show();
                }



            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Starting intent
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Region r = (Region) regionsSpinner.getSelectedItem();
                String url = "/city?city=" + r.getId();
                new DropdownCity(citiesSpinner, url, EditProfile.this, cities).execute();
                Log.e("MainActivity", r.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Log.e("MainActivity", "edit profile activity -------------------------");


        new DropdownCategory(categorySpinner, "category", this, categories).execute();
        new DropdownRegions(regionsSpinner, "region", this).execute();
    }


    //Get image from gallery
    //Calling the onActivityResult to handle the selected image from the intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GETTING IMAGE FROM GALLERY
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = BitmapFactory.decodeFile(picturePath);

            try {
                bitmap = ImgHandler.modifyOrientation(bmp, picturePath);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private boolean validation(){
        Log.e("MainActivity", "Validation");
        for(TextView view: text_views) {
            if (view.getText().toString().matches("") || view.getText().toString().length()<0)
                return false;

        }
        return true;
    }

    private class ProfileEditHttp extends AsyncTask<Void, Void, Boolean>{
        private AppProfile appProfile;

        public ProfileEditHttp() {


            appProfile = new AppProfile();
            Profile p = new Profile();
            p.setName(String.valueOf(name.getText()));
            p.setDescription(description.getText().toString());
            p.setEmail(AccountSingleton.getCurrent().getEmail());
            p.setId(AccountSingleton.getCurrent().getId());
            p.setSurname(surname.getText().toString());
            p.setTelephone(phone.getText().toString());
            p.setTitle(title.getText().toString());

            Log.e("MainActivity", cities.get(citiesSpinner.getSelectedItemPosition()).toString());

            p.setCategory(String.valueOf(categories.get((int) categorySpinner.getSelectedItemId()).getId()));
            p.setCity(String.valueOf(cities.get((int) citiesSpinner.getSelectedItemId()).getId()));

            appProfile.setProfile(p);
            appProfile.setImage_bytes(ImgHandler.convBmtToArray(bitmap));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = Url.getUrl() + "register-new-profile";
            Boolean response = false;

            Log.e("MainActivity", url);

            try {

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                Log.e("MainActivity", "After rest template");

                response = restTemplate.postForObject(url, appProfile, Boolean.class);

                Log.e("MainActivity", response.toString());

                return response;

            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(Boolean o) {
            super.onPostExecute(o);


            //end progress dialog
            if(pg.isShowing())
                pg.dismiss();

            if(o) {
                Intent i = new Intent(EditProfile.this, MainActivity.class);
                i.putExtra("refresh","refresh");
                startActivity(i);

                EditProfile.this.finish();
            }
            else{
                Toast.makeText(EditProfile.this, "Aveti deja un profil activ!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
