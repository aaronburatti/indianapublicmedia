package com.example.nebulese.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlaylist extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //youtube variable
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayer;
    private static String YOUTUBE_API_KEY = "AIzaSyCfMG8Hi7yY76ch5-PpPXkQfYppWpXgDP8";
    private static String PlayList_ID = "";
    private static final int RECOVERY_DIALOG_REQUEST = 1;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_playlist);

        //get the bundle and parse the identifying infor
        Bundle bun = getIntent().getExtras();
        //look for the index and get the appropriate playlist
        if(bun.getInt("index") == 1) {
            PlayList_ID = bun.getString("incard");
        } else {
            PlayList_ID = bun.getString("indroid");
        }
        //initialize the youtube view and play the playlist
        youTubePlayer = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayer.initialize(YOUTUBE_API_KEY, this);

    }


    /*
    these are all boilerplate youtube methods to run the playlist
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failure to Initialize!", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        /** Start buffering **/
        if (!wasRestored) {
            player.cuePlaylist(PlayList_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };
}
