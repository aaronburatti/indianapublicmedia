package com.example.nebulese.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import static com.example.nebulese.myapplication.R.string.player_error;

public class Television extends AppCompatActivity {
    //set the flag which looks for a created state and brings it to the
    //front of the stack
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    //register the menu items
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;
    ImageButton incard;
    ImageButton indroid;
    private static String indiananewsdesk = "PLsLvHNXs74oABQAwdocnfKJDdd1Vt6eIT";
    private static String indiandroid = "PLsLvHNXs74oDjl92pCVCZNiyBXsS81V6Q";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_television);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //make sure these gui components are available when activity starts
        action_home = (MenuItem)findViewById(R.id.action_home);
        action_wfiu = (MenuItem)findViewById(R.id.action_wfiu);
        action_wtiu = (MenuItem)findViewById(R.id.action_wtiu);
        incard      = (ImageButton)findViewById(R.id.indiananewsdesk);
        indroid     = (ImageButton) findViewById(R.id.indiandroid);
        Picasso.get().load("https://indianapublicmedia.org/news/news-images/newsdesk-header-1.png").resize(1000, 300).into(incard);
        Picasso.get().load("https://indianapublicmedia.org/digital1229/files/2018/06/Indiandroid-logo.png").resize(1000, 200).into(indroid);
        //
    }

    public void onincardClick(View view){

        Intent intent = new Intent(Television.this, YoutubePlaylist.class);
        intent.putExtra("incard", indiananewsdesk);
        intent.putExtra("index", 1);
        startActivity(intent);
    }

    public void onindroidClick(View view){

        Intent intent = new Intent(Television.this, YoutubePlaylist.class);
        intent.putExtra("indroid", indiandroid);
        intent.putExtra("index", 2);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This switch statement takes the ID of the menu item passed into the function
        //as the criterion by which it uses to correlate the click with an activity to be called
        switch(item.getItemId()){
            //if home menu item was chosen
            case R.id.action_home:
                //create new intent specific to the Main Activity
                Intent intentHome = new Intent(this, MainActivity.class);
                //add the flag
                intentHome.addFlags(flag);
                //pass the intent activity method
                startActivity(intentHome);
                //satisfy boolean switch statement, exit the case
                return true;
            //if home wfiu item was chosen
            case R.id.action_wfiu:
                //create new intent specific to the Radio Activity
                Intent intentWFIU = new Intent(this, Radio.class);
                //add the flag
                intentWFIU.addFlags(flag);
                //pass the intent activity method
                startActivity(intentWFIU);
                //satisfy boolean switch statement, exit the case
                return true;
            //if home wtiu item was chosen
            case R.id.action_wtiu:
                //create new intent specific to the Television Activity
                Intent intentWTIU = new Intent(this, Television.class);
                //add the flag
                intentWTIU.addFlags(flag);
                //pass the intent activity method
                startActivity(intentWTIU);
                //satisfy boolean switch statement, exit the case
                return true;
            default:
                return true;

        }

    }



}


