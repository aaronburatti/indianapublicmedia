package com.example.nebulese.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/*

A lot of this was taken from the youtube data api pages

*/

public class Television extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //set the flag which looks for a created state and brings it to the
    //front of the stack
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    //register the menu items
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;

    //youtube variable
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_television);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Since this class now extends the youtubebase class and not appcompat I need
        //to find a way to display the toolbar
        //setSupportActionBar(toolbar);

        //make sure these gui components are available when activity starts
        action_home = (MenuItem)findViewById(R.id.action_home);
        action_wfiu = (MenuItem)findViewById(R.id.action_wfiu);
        action_wtiu = (MenuItem)findViewById(R.id.action_wtiu);

        //make the youtube components available
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

    }

    private final class Config {

        private Config() {
        }
        //for security reasons, hold the API key in its own private class
        public static final String YOUTUBE_API_KEY = "AIzaSyCfMG8Hi7yY76ch5-PpPXkQfYppWpXgDP8";

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
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        //load a video by default
        if (!wasRestored) {
            player.cueVideo("bPW6qGED0r0");
        }
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
           // handle the errors
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Re-initialize if user performed a recovery action
            youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

}
