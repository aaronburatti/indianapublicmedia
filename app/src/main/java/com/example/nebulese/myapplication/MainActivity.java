package com.example.nebulese.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    ImageButton leadImageButton;
    ImageButton shareImageButton;
    VideoView videoView;
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leadImageButton = (ImageButton)findViewById(R.id.leadImage);
        shareImageButton = (ImageButton)findViewById(R.id.shareIcon);
        videoView = (VideoView)findViewById(R.id.videoStory);
        action_home = (MenuItem)findViewById(R.id.action_home);
        action_wfiu = (MenuItem)findViewById(R.id.action_wfiu);
        action_wtiu = (MenuItem)findViewById(R.id.action_wtiu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.action_home:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                return true;
            case R.id.action_wfiu:
                Intent intentWFIU = new Intent(this, Radio.class);
                startActivity(intentWFIU);
                return true;
            case R.id.action_wtiu:

                return true;
            default:
                return true;

        }

    }

    public void onBookmarkClick(View view){
        Intent intent = new Intent(this, Radio.class);
        startActivity(intent);
    }

    public void onStoryImageClick(View view){
        Intent intent = new Intent(this, Story.class);
        startActivity(intent);
    }

    public void onStoryShareClick(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String title = "Share Via...";
        Intent chooseIntent = Intent.createChooser(intent, title);
        startActivity(chooseIntent);
    }

}
