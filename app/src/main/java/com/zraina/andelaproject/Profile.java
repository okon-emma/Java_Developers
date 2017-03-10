package com.zraina.andelaproject;

/**
 * Created by Okon on 2017-03-07.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private String url, name, img, share_content;
    private TextView txt;
    private RoundedImageView prof_img;
    private Button page, share_whatsap, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        actionBar.setTitle("Profile Page");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });

        txt = (TextView)findViewById(R.id.textView);
        prof_img = (RoundedImageView)findViewById(R.id.imageView1);
        page = (Button)findViewById(R.id.page);
        share_whatsap = (Button)findViewById(R.id.share_whatsap);
        share = (Button)findViewById(R.id.share);

        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("html_url");
        name = bundle.getString("name");
        img = bundle.getString("img");
        share_content =  "Check out this awesome developer @" + name + ", " + url;
        txt.setText(name);

        Picasso.with(Profile.this)
                .load(img)
                .placeholder(R.drawable.ic_test)
                .error(R.drawable.ic_test)
                .into(prof_img);

        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(url));
                startActivity(Intent.createChooser(myWebLink, getResources().getText(R.string.send_to)));
            }
        });

        share_whatsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, share_content);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(v, "Whatsapp not Installed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share_content);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_profile, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_visit:
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(url));
                startActivity(Intent.createChooser(myWebLink, getResources().getText(R.string.send_to)));
                return true;
            case R.id.action_about:
                new AlertDialog.Builder(Profile.this)
                        .setView(getLayoutInflater().inflate(R.layout.dialog_about, null))
                        .show();
                return true;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share_content);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
