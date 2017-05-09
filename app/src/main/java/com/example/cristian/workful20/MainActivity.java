package com.example.cristian.workful20;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.workful.Tools.AccountSingleton;
import com.workful.Tools.EndlessRecyclerViewScrollListener;
import com.workful.Tools.SearchAdapter;
import com.workful.Tools.HttpCommunication.SearchHttpRequest;
import com.workful.templates.SearchResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView account_name, account_email;
    private ImageView navigation_image;
    private Menu drawer_menu;
    private Intent intent;

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    private ArrayList<SearchResult> lst = new ArrayList<>();

    private String search_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settingUpContent(toolbar);


        refresh();

        intent = getIntent();
        handleIntent(intent);


    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items

        new SearchHttpRequest(search_url, this, adapter, offset, lst).execute();
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    /*
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
*/

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            search_url = "search?query="+query;

            newSearchNotify();
            new SearchHttpRequest(search_url, this, adapter, 0, lst).execute();

            Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();

        }else if(intent.getExtras() != null){
            if(intent.getExtras().get("oras_id")!=null){

                newSearchNotify();

                String query = (String)intent.getExtras().get("query");
                int id_oras = intent.getIntExtra("oras_id",0);
                int id_categorie = intent.getIntExtra("categorie_id", 0);

                search_url = String.format("search?query=%s&city=%s&category=%s",query, id_oras, id_categorie);

                new SearchHttpRequest(search_url, this, adapter, 0, lst).execute();

            }
            else
             refresh();
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
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);

        } else if (id == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Log out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                           if(AccountSingleton.getCurrent()!=null) {
                               AccountSingleton.getCurrent().accountLogout();
                               if (android.os.Build.VERSION.SDK_INT >= 11){
                                    //Code for recreate
                                   MainActivity.this.recreate();

                               }else{
                                    //Code for Intent
                                   finish();
                                   startActivity(intent);
                               }
                           }
                            else
                                Log.e("MainActivity", "No user logged in");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
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

            if(!AccountSingleton.getCurrent().getFull_name().equals("")) {
                account_name.setText(AccountSingleton.getCurrent().getFull_name());
            //    new ImageDownloader(navigation_image, AccountSingleton.getCurrent().getEmail()).execute();
                navigation_image.setVisibility(View.VISIBLE);
            }
            else
                account_name.setVisibility(View.GONE);
        }
    }

    private void newSearchNotify(){
        // 1. First, clear the array of data
        lst.clear();
        Log.e("MainActivity", lst.toString());
        // 2. Notify the adapter of the update
        adapter.notifyDataSetChanged();
        // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
    }

    private void settingUpContent(Toolbar toolbar){
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        Log.e("MainActivity", "main activity -------------------------");

        drawer_menu = navigationView.getMenu();


        /***
         *  Endless scroll and RecyclerView setup
         */
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(), 2);

        recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), false));

        adapter = new SearchAdapter(this, lst);

        recyclerView.setAdapter(adapter);


        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager, 2) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);


    }

}

