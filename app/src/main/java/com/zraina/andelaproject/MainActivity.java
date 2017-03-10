package com.zraina.andelaproject;

/**
 * Created by Okon on 2017-03-08.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar simpleProgressBar;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private static String url_30 = "https://api.github.com/search/users?q=+language:%22java%22+location:%22lagos%22&page=1&per_page=30";
    private static String url_50 = "https://api.github.com/search/users?q=+language:%22java%22+location:%22lagos%22&page=1&per_page=50";
    private static String url_70 = "https://api.github.com/search/users?q=+language:%22java%22+location:%22lagos%22&page=1&per_page=70";
    private static String url_80 = "https://api.github.com/search/users?q=+language:%22java%22+location:%22lagos%22&page=1&per_page=80";
    private static String url_100 = "https://api.github.com/search/users?q=+language:%22java%22+location:%22lagos%22&page=1&per_page=100";
    private String[] n_limit = {"30 (default)", "50", "70", "80", "100"};

    ArrayList<Users> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();

        sharedPref = getSharedPreferences("cache", MODE_PRIVATE);
        editor = sharedPref.edit();

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator);
        simpleProgressBar=(ProgressBar) findViewById(R.id.simpleProgressBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            simpleProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            SharedPreferences sharedCache = getSharedPreferences("cache", MODE_PRIVATE);
            String url = sharedCache.getString("url_pass", url_30);

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("items");

                    // looping through All Users
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String name = c.getString("login");
                        String p_img = c.getString("avatar_url");
                        String id = c.getString("html_url");

                        Users contact = new Users(name, p_img, id);

                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Unable to Load data", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Refresh", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            contactList = new ArrayList<>();
                                            new GetContacts().execute();
                                        }
                                    });
                            snackbar.show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Check Internet Connection", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Refresh", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        contactList = new ArrayList<>();
                                        new GetContacts().execute();
                                    }
                                });
                        snackbar.show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress Bar
            simpleProgressBar.setVisibility(View.GONE);

            /**
             * Updating parsed JSON data into RecyclerView
             **/
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(contactList, MainActivity.this);
            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);

            recyclerView.addOnItemTouchListener
                    (new RecyclerItemClickListener(MainActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(MainActivity.this, Profile.class);
                            intent.putExtra("html_url", contactList.get(position).getUrl());
                            intent.putExtra("name", contactList.get(position).getLogin());
                            intent.putExtra("img", contactList.get(position).getImg());
                            startActivity(intent);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_limit:
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.my_dialog_layout, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptsView);

                // Setting Positive Button
                alertDialogBuilder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        Snackbar snackbar1 = Snackbar
                                .make(coordinatorLayout, "Feed Limit Value Saved", Snackbar.LENGTH_LONG)
                                .setAction("Refresh", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        contactList = new ArrayList<>();
                                        new GetContacts().execute();
                                    }
                                });
                        snackbar1.show();
                    }

                });

                // Setting Negative Button
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        dialog.cancel();
                    }
                });


                final AlertDialog alertDialog = alertDialogBuilder.create();

                final Spinner mSpinner= (Spinner) promptsView
                        .findViewById(R.id.mySpinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, n_limit);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                mSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                return true;

            case R.id.action_visit:
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://github.com/"));
                startActivity(Intent.createChooser(myWebLink, getResources().getText(R.string.send_to)));
                return true;

            case R.id.action_about:
                new AlertDialog.Builder(MainActivity.this)
                        .setView(getLayoutInflater().inflate(R.layout.dialog_about, null))
                        .show();
                return true;

            case R.id.action_refresh:
                contactList = new ArrayList<>();
                new GetContacts().execute();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class OnSpinnerItemClicked implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (pos==0){editor.putString("url_pass", url_30);
                editor.apply();}

            if (pos==1){editor.putString("url_pass", url_50);
                editor.apply();}

            if (pos==2){editor.putString("url_pass", url_70);
                editor.apply();;}

            if (pos==3){editor.putString("url_pass", url_80);
                editor.apply();}

            if (pos==4){editor.putString("url_pass", url_100);
                editor.apply();}

        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

}