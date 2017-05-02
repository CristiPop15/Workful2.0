package com.example.cristian.workful20;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.workful.Tools.AccountSingleton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView account_name, account_email;
    private ImageView navigation_image;
    private Menu drawer_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        account_name = (TextView)headerView.findViewById(R.id.accountName);
        account_email = (TextView)headerView.findViewById(R.id.accountEmail);
        navigation_image = (ImageView)headerView.findViewById(R.id.imageView);

        drawer_menu = navigationView.getMenu();

        refresh();

        handleIntent(getIntent());

    }



    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_filter){
            Intent i = new Intent(this, FilterActivity.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login) {
            Intent i = new Intent(getBaseContext(), Login.class);
            startActivity(i);
        } else if (id == R.id.filter) {
            Intent i = new Intent(this, FilterActivity.class);
            startActivity(i);

        } else if (id == R.id.help) {


        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        } else if (id == R.id.edit_profile) {

        } else if (id == R.id.register_account) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void refresh() {
        if(AccountSingleton.getCurrent() == null){
            drawer_menu.findItem(R.id.logout).setVisible(false);
            drawer_menu.findItem(R.id.settings).setVisible(false);
            drawer_menu.findItem(R.id.edit_profile).setVisible(false);
            drawer_menu.findItem(R.id.login).setVisible(true);

            account_email.setText("www.workful.com");
            account_name.setText("Workful");

            navigation_image.setVisibility(View.GONE);

        }
        else{
            drawer_menu.findItem(R.id.logout).setVisible(true);
            drawer_menu.findItem(R.id.settings).setVisible(true);
            drawer_menu.findItem(R.id.edit_profile).setVisible(true);
            drawer_menu.findItem(R.id.login).setVisible(false);

            account_email.setText(AccountSingleton.getCurrent().getEmail());

            if(!AccountSingleton.getCurrent().getFull_name().equals(""))
                account_name.setText(AccountSingleton.getCurrent().getFull_name());

            navigation_image.setVisibility(View.VISIBLE);


        }
    }

}
