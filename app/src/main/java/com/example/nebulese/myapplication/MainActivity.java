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
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
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
    
    @Override
    protected void onResume(){
        super.onResume();
    }

    public void onBookmarkClick(View view){

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