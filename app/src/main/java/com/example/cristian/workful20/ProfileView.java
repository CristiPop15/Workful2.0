package com.example.cristian.workful20;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.workful.Tools.AccountSingleton;
import com.workful.Tools.CommentAdapter;
import com.workful.Tools.HttpCommunication.AddCommentHttp;
import com.workful.Tools.HttpCommunication.ImageDownloader;
import com.workful.Tools.RatingAdapter;
import com.workful.templates.Comm;
import com.workful.templates.Comment;
import com.workful.templates.Profile;
import com.workful.templates.RegionList;
import com.workful.templates.Url;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class ProfileView extends AppCompatActivity {

    private ImageView profile_image;
    private Profile p;
    private TextView description, name, city, phoneNr;
    private RecyclerView recyclerView_skill, recyclerView_comment;
    private ProgressDialog pg;
    private ArrayList<Comment> comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        p = (Profile)getIntent().getSerializableExtra("selectedProfile");

        pg = new ProgressDialog(this);

        comments = new ArrayList<>();

        CollapsingToolbarLayout cltbl = (CollapsingToolbarLayout)findViewById(R.id.collapsing);

        profile_image = (ImageView)findViewById(R.id.profileImg);
        description = (TextView)findViewById(R.id.textD);
        city = (TextView)findViewById(R.id.textC);
        phoneNr = (TextView)findViewById(R.id.phoneNumber);
        name = (TextView)findViewById(R.id.full_name);

        recyclerView_skill = (RecyclerView)findViewById(R.id.recycler_view_rating);
        recyclerView_comment = (RecyclerView)findViewById(R.id.recycler_view_comment);

        RatingAdapter adapter = new RatingAdapter(p.getSkills());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getBaseContext());



        recyclerView_comment.setLayoutManager(mLayoutManager2);

        new CommentHttp(p.getId()).execute();

        recyclerView_skill.setLayoutManager(mLayoutManager);

        recyclerView_skill.setAdapter(adapter);

        cltbl.setTitle(p.getName());
        Picasso.with(this).load(Url.image_url(p.getEmail())).into(profile_image);

        description.setText(p.getDescription());
        city.setText(p.getCity());
        name.setText(p.getName()+" "+p.getSurname());

        phoneNr.setText(p.getTelephone());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab_comment = (FloatingActionButton) findViewById(R.id.fabComment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNr.getText().toString()));
                startActivity(intent);
            }
        });


        fab_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountSingleton.getCurrent() != null){
                    showChangeLangDialog();
                }
                else{
                    Toast.makeText(ProfileView.this, "Nu puteti adauga comentarii deoarece nu sunteti logat", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class CommentHttp extends AsyncTask<Void,Void, ArrayList<Comment>>{

        private String id;

        public CommentHttp(int id) {
            this.id = String.valueOf(id);
        }

        @Override
        protected ArrayList<Comment> doInBackground(Void... params) {
            String url = Url.getUrl()+"/get-comments?id="+id;
            Log.e("MainActivity", url);

            Comm comm = null;


            try {

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                comm = restTemplate.getForObject(url, Comm.class);

                Log.e("MainActivity", comm.comments.toString());


            }catch (Exception e){
                e.printStackTrace();
            }

            return comm.comments;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.setMessage("Loading..");
            pg.setCancelable(false);
            pg.show();
        }



        @Override
        protected void onPostExecute(ArrayList<Comment> o) {
            super.onPostExecute(o);

            CommentAdapter com_adapter = new CommentAdapter(o, ProfileView.this);
            recyclerView_comment.setAdapter(com_adapter);

            //end progress dialog
            if(pg.isShowing())
                pg.dismiss();

        }
    }


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_add_comment, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.comment);
        final RatingBar rating = (RatingBar) dialogView.findViewById(R.id.rating);
        rating.setRating(1);


        dialogBuilder.setTitle("Adaugati comentariu pentru "+p.getName());
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = edt.getText().toString();
                if(!text.matches(""))
                    new AddCommentHttp(ProfileView.this, text, String.valueOf(Math.round(rating.getRating())), AccountSingleton.getCurrent().getId(), p.getId(), ProfileView.this).execute();
                else
                    Toast.makeText(ProfileView.this, "Introduceti valori!", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
