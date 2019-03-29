package com.example.nebulese.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.nebulese.myapplication.R;


public class Radio extends AppCompatActivity {
    //set the flag which looks for a created state and brings it to the
    //front of the stack
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    //register the menu items
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;
    ImageView img;
    VideoView videoView;
    private String radioUri = "https://npr-hls.leanstream.co/npr/WFIUFM.stream/playlist.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //make sure these gui components are available when activity starts
        action_home = (MenuItem)findViewById(R.id.action_home);
        action_wfiu = (MenuItem)findViewById(R.id.action_wfiu);
        action_wtiu = (MenuItem)findViewById(R.id.action_wtiu);
        img = (ImageView)findViewById(R.id.radioPlayButton);
        videoView = (VideoView)findViewById(R.id.wfiuOne);


//
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

    private void setUpRadioStream(View view, String radioUri) {
        Log.i("rad", "uri" + radioUri);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        //String uri = "https://npr-hls.leanstream.co/npr/WFIUF2.stream/playlist.m3u8";

        videoView.setVideoURI(Uri.parse(radioUri));


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
            }


    });




}
    public void wfiuOnePlayClick(View view){

        Log.i("play","playing" + videoView.isPlaying());
        if(videoView.isPlaying() == false) {
            setUpRadioStream(view, radioUri);
            videoView.start();
            img.setImageResource(R.mipmap.pauseicon);
        }else if(videoView.isPlaying() == true){
            videoView.stopPlayback();
            img.setImageResource(R.mipmap.playicon);
        }
    }

    public void radioNextButton(View view) {
        if(radioUri == "https://npr-hls.leanstream.co/npr/WFIUF2.stream/playlist.m3u8"){
            radioUri = "https://npr-hls.leanstream.co/npr/WFIUFM.stream/playlist.m3u8";
            setUpRadioStream(view, radioUri);
            videoView.start();
        }else {
            radioUri = "https://npr-hls.leanstream.co/npr/WFIUF2.stream/playlist.m3u8";
            setUpRadioStream(view, radioUri);
            videoView.start();
        }

    }

}
